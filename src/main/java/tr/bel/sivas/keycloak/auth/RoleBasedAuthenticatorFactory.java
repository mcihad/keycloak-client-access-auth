package tr.bel.sivas.keycloak.auth;


import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

public class RoleBasedAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "role-based-authenticator";

    @Override
    public String getDisplayType() {
        return "Role Based Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[] {
                AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public String getHelpText() {
        return "Restricts login to users with {client-id}-access role.";
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new RoleBasedAuthenticator();
    }

    @Override
    public void init(Config.Scope config) {
        // Başlatma işlemleri
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Ek başlatma sonrası işlemler
    }

    @Override
    public void close() {
        // Kaynakları temizle
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}