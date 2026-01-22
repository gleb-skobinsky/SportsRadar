import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUserResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String? = null,
    @SerialName("role")
    val role: String,
    @SerialName("merchant_id")
    val merchantId: Int,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("email")
    val email: String,
    @SerialName("sanction_list_item_id")
    val sanctionListItemId: String? = null,
    @SerialName("phone_verified_at")
    val phoneVerifiedAt: String? = null,
    @SerialName("remember_token")
    val rememberToken: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("privileges")
    val privileges: List<String>,
    @SerialName("brands")
    val brands: List<ApiUserBrand>,
    @SerialName("status")
    val status: String,
    @SerialName("need_otp")
    val needOtp: Boolean,
    @SerialName("application")
    val application: Application? = null,
    @SerialName("balances")
    val balances: List<ApiUserBalance>
)

@Serializable
data class ApiUserBrand(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("params")
    val params: BrandParams,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("pivot")
    val pivot: Pivot
)

@Serializable
data class BrandParams(
    @SerialName("sms_driver")
    val smsDriver: String,
    @SerialName("2fa_enabled")
    val twoFaEnabled: Boolean,
    @SerialName("auth_method")
    val authMethod: String,
    @SerialName("otp_enabled")
    val otpEnabled: Boolean,
    @SerialName("validate_form")
    val validateForm: Boolean,
    @SerialName("default_form_id")
    val defaultFormId: Int,
    @SerialName("need_onboarding")
    val needOnboarding: Boolean,
    @SerialName("otp_need_auth_user")
    val otpNeedAuthUser: Boolean,
    @SerialName("onboarding_application_status")
    val onboardingApplicationStatus: String
)

@Serializable
data class Pivot(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("brand_id")
    val brandId: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

@Serializable
data class Application(
    @SerialName("form_id")
    val formId: Int,
    @SerialName("data")
    val data: ApplicationData,
    @SerialName("status")
    val status: String,
    @SerialName("form")
    val form: Form
)

@Serializable
data class ApplicationData(
    @SerialName("full_company_name")
    val fullCompanyName: String
)

@Serializable
data class Form(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("enabled")
    val enabled: Int,
    @SerialName("brand_id")
    val brandId: Int,
    @SerialName("order")
    val order: Int,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("sections")
    val sections: List<Section>
)

@Serializable
data class Section(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("alias")
    val alias: String,
    @SerialName("order")
    val order: Int,
    @SerialName("form_id")
    val formId: Int,
    @SerialName("enabled")
    val enabled: Int,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

@Serializable
data class ApiUserBalance(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("project_uuid")
    val projectUuid: String
)
