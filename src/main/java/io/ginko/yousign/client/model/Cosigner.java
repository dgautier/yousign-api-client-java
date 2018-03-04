package io.ginko.yousign.client.model;

import com.google.common.collect.Lists;
import io.ginko.yousign.client.cosign.CosignerInfosParams;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Builder
public class Cosigner {


    private String firstName;
    private String lastName;

    @Email
    @Valid
    private String mail;

    @NotNull
    @Pattern(regexp = "^\\+\\d{10,14}$",
            message = "Phone number must be formatted to E164")
    @Valid
    private String phone;

    protected ProofLevel proofLevel;

    protected AuthenticationMode authenticationMode;
    protected String authenticationValue;
    protected String persoSms;

    private VisibleSignature visibleSignature;

    public CosignerInfosParams toCosignerInfosParams() {
        final CosignerInfosParams infosParams = new CosignerInfosParams();
        infosParams.setFirstName(getFirstName());
        infosParams.setLastName(getLastName());
        infosParams.setMail(getMail());
        infosParams.setPhone(getPhone());
        infosParams.setProofLevel(getProofLevel().name());
        if (getAuthenticationMode() != null){
            infosParams.setAuthenticationMode(getAuthenticationMode().name());
        }

        return infosParams;
    }
}
