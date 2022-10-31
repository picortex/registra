import expect.expect
import expect.expectFailure
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import registra.VerificationViewModel
import kotlin.test.Test

class ParseEmailAndTokenTest {

    @Test
    fun should_parse_email_and_token_if_they_are_all_present() = runTest {
        val link = "https://picortex.com/verify?email=andy@lama.com&token=token-1"
        val params = VerificationViewModel.parseEmailAndToken(link).await()
        expect(params.email).toBe("andy@lama.com")
        expect(params.token).toBe("token-1")
    }

    @Test
    fun should_fail_to_parse_link_when_it_doesnt_have_an_email() = runTest {
        val link = "https://picortex.com/verify?token=token-1"
        val error = expectFailure { VerificationViewModel.parseEmailAndToken(link).await() }
        expect(error.message).toBe(VerificationViewModel.EMAIL_NOT_FOUND_IN_LINK.message)
    }
}