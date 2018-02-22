package io.ginko.yousign.client.model;

import io.ginko.yousign.client.cosign.CosignerInfosParams;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class Cosigner {

    private String firstName;
    private String lastName;

    @Email
    private String mail;

    @NotNull
    @Pattern(regexp = "^\\+\\d{10,14}$",
            message = "Phone number must be formatted to E164")
    private String phone;

    protected ProofLevel proofLevel;

    protected AuthenticationMode authenticationMode;
    protected String authenticationValue;
    protected String persoSms;

    public CosignerInfosParams toCosignerInfosParams() {
        final CosignerInfosParams infosParams = new CosignerInfosParams();
        infosParams.setFirstName(getFirstName());
        infosParams.setLastName(getLastName());
        infosParams.setMail(getMail());
        infosParams.setPhone(getPhone());
        infosParams.setProofLevel(getProofLevel().name());
        infosParams.setAuthenticationMode(getProofLevel().name());

        return infosParams;
    }
}
