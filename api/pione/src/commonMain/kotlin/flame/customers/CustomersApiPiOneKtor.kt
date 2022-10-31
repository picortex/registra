package flame.customers

import bitframe.ApiConfigKtor
import bitframe.actor.Identified
import cabinet.PiOneRootConfig
import cabinet.RootDir
import cabinet.RootDirPiOneKtor
import flame.CustomersApi
import flame.customers.params.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.collections.interoperable.List
import koncurrent.Later
import koncurrent.later
import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.serialization.json.*
import kronecker.LoadOptions
import pione.*
import pione.response.PiOneSingleDataSuccessResponse

class CustomersApiPiOneKtor(override val config: ApiConfigKtor<PiOneEndpoint>) : PiOneApi by PiOneApi(config), CustomersApi {

    private val parser by lazy { CustomerParserPiOne() }
    override fun create(params: CorporateCustomerParams): Later<CorporateCustomer> = config.scope.later {
        val response = client.post(path.adoed) {
            setBody(config.content(PiOneEndpoint.DataType.Customer, params.toPiOneCustomer()))
        }
        val text = response.bodyAsText()
        val serializer = PiOneSingleDataSuccessResponse.serializer(PiOneCustomer.serializer())
        try {
            codec.decodeFromString(serializer, text).obj.toCustomer() as CorporateCustomer
        } catch (e: Exception) {
            handleError(text, e)
        }
    }

    override fun create(params: IndividualCustomerParams): Later<IndividualCustomer> = config.scope.later {
        val response = client.post(path.adoed) {
            val piOneCustomer = params.toPiOneCustomer()
            setBody(config.content(PiOneEndpoint.DataType.Customer, piOneCustomer))
        }
        val text = response.bodyAsText()
        val serializer = PiOneSingleDataSuccessResponse.serializer(PiOneCustomer.serializer())
        try {
            codec.decodeFromString(serializer, text).obj.toCustomer() as IndividualCustomer
        } catch (e: Exception) {
            handleError(text, e)
        }
    }

    override fun load(options: LoadOptions): Later<List<Customer>> = config.scope.later {
        data(PiOneEndpoint.DataType.Customer, PiOneCustomer.serializer(), options).map {
            it.toCustomer()
        }.toInteroperableList()
    }

    override fun load(uid: String): Later<Customer> = config.scope.later {
        data(PiOneEndpoint.DataType.Customer, PiOneCustomer.serializer(), uid).toCustomer()
    }

    val GenerateCustomerStatementParams.filename get() = "Customer_Statement_$customerId.pdf"
    override fun generateStatementURL(params: GenerateCustomerStatementParams): Later<String> = config.scope.later {
        val pattern = "{MMM} {DD}, {YYYY}"
        val response = client.post(path.adoed) {
            val p = mapOf(
                "dateFrom" to params.start.format(pattern),
                "dateTo" to params.end.format(pattern),
                "customer" to params.customerId
            )
            setBody(config.content(PiOneEndpoint.DataType.GenerateCustomerStatement, p))
        }
        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)
        val possibleError = RuntimeException(text)
        println(text)
        if (result["objectId"]?.jsonPrimitive?.content != null) {
            result["object"]?.jsonObject?.get("url")?.jsonPrimitive?.contentOrNull ?: throw RuntimeException("Api returned null: Unknown error", possibleError)
        } else {
            throw possibleError
        }
    }

    override fun editIndividual(params: Identified<String, IndividualCustomerParams>): Later<IndividualCustomer> = config.scope.later {
        val response = client.post(path.adoed) {
            val piOneCustomer = params.body.toPiOneCustomer(params.uid)
            setBody(config.content(PiOneEndpoint.DataType.Customer, piOneCustomer))
        }

        val text = response.bodyAsText()
        val serializer = PiOneSingleDataSuccessResponse.serializer(PiOneCustomer.serializer())
        try {
            codec.decodeFromString(serializer, text).obj.toCustomer() as IndividualCustomer
        } catch (e: Exception) {
            handleError(text, e)
        }
    }

    override fun editCorporate(params: Identified<String, CorporateCustomerParams>): Later<CorporateCustomer> = config.scope.later {
        val response = client.post(path.adoed) {
            val piOneCustomer = params.body.toPiOneCustomer(params.uid)
            setBody(config.content(PiOneEndpoint.DataType.Customer, piOneCustomer))
        }

        val text = response.bodyAsText()
        val serializer = PiOneSingleDataSuccessResponse.serializer(PiOneCustomer.serializer())
        try {
            codec.decodeFromString(serializer, text).obj.toCustomer() as CorporateCustomer
        } catch (e: Exception) {
            handleError(text, e)
        }
    }

    override fun sendSMS(params: SendCustomerSMSParams): Later<SendCustomerSMSParams> = config.scope.later {
        val response = client.post(path.adoed) {
            val p = mapOf(
                "message" to params.message,
                "channel" to "SMS",
                "customer" to params.customerId,
                "mobile" to params.mobile
            )
            setBody(config.content(PiOneEndpoint.DataType.SendMessageToCustomer, p))
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)
        if (result["objectId"]?.jsonPrimitive?.content != null) {
            params
        } else {
            throw RuntimeException(text)
        }
    }

    override fun sendEmail(params: SendCustomerEmailParams): Later<SendCustomerEmailParams> = config.scope.later {
        val response = client.post(path.adoed) {
            val p = mapOf(
                "emailBody" to params.emailBody,
                "subject" to params.subject,
                "channel" to "EMAIL",
                "customer" to params.customerId,
                "email" to params.email
            )
            setBody(config.content(PiOneEndpoint.DataType.SendMessageToCustomer, p))
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)
        if (result["objectId"]?.jsonPrimitive?.content != null) {
            params
        } else {
            throw RuntimeException(text)
        }
    }

    override fun delete(customer: Customer): Later<Customer> = config.scope.later {
        val response = client.post(path.delete) {
            val p = mapOf(
                "ids" to listOf(customer.uid)
            )
            setBody(config.content(PiOneEndpoint.DataType.Customer, p))
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)
        if (result["status"]?.jsonPrimitive?.content == "ok") {
            customer
        } else {
            throw RuntimeException(text)
        }
    }

    override fun deleteBulk(customers: List<Customer>): Later<List<Customer>> = config.scope.later {
        val response = client.post(path.delete) {
            val p = mapOf(
                "ids" to customers.map { it.uid }
            )
            setBody(config.content(PiOneEndpoint.DataType.Customer, p))
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)
        if (result["status"]?.jsonPrimitive?.content == "ok") {
            customers
        } else {
            throw RuntimeException(text)
        }
    }


    override fun canCreate(): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.Customer, PiOneEndpoint.PermissionType.ADD)
    }

    override fun canList(): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.Customer, PiOneEndpoint.PermissionType.LIST)
    }

    override fun canEdit(customer: Customer): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.Customer, PiOneEndpoint.PermissionType.EDIT, customer.uid)
    }

    override fun canDelete(customer: Customer): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.Customer, PiOneEndpoint.PermissionType.DELETE, customer.uid)
    }

    override fun canImport(): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.Customer, PiOneEndpoint.PermissionType.IMPORT)
    }

    override fun canSendMessage(customer: Customer): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.SendMessageToCustomer, PiOneEndpoint.PermissionType.ADD)
    }

    override fun canGenerateStatement(customer: Customer): Later<Boolean> = config.scope.later {
        permission(PiOneEndpoint.DataType.GenerateCustomerStatement, PiOneEndpoint.PermissionType.ADD)
    }

    override fun getRootDir(customerId: String): RootDir = RootDirPiOneKtor(
        configuration = PiOneRootConfig(id = customerId, type = PiOneEndpoint.DataType.Customer, api = config)
    )

    override fun getRootDir(customer: Customer): RootDir = getRootDir(customer.uid)
}