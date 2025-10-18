package tr.bel.sivas.keycloak.auth;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.ClientModel;

public class RoleBasedAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        RealmModel realm = context.getRealm();
        ClientModel client = context.getAuthenticationSession().getClient();

        if (user == null) {
            // Kullanıcı yoksa, hata mesajıyla oturumu sonlandır
            Response errorResponse = context.form()
                    .setError("Giriş yapacak kullanıcı bulunamadı.")
                    .createErrorPage(Response.Status.UNAUTHORIZED);
            context.failure(AuthenticationFlowError.INVALID_USER, errorResponse);
            return;
        }

        // Dinamik olarak client-id-access rolünü oluştur
        String requiredRole = client.getClientId() + "-access";
        RoleModel role = realm.getRole(requiredRole);

        if (role == null) {
            // Rol yoksa, hata mesajını kullanıcıya göster
            Response errorResponse = context.form()
                    .setError("Uygulama için gerekli rol yoktur. Yönetici ile iletişime geçin. Gerekli Rol(Realm): "+requiredRole)
                    .createErrorPage(Response.Status.FORBIDDEN);
            context.failure(AuthenticationFlowError.CLIENT_NOT_FOUND, errorResponse);
            context.getEvent().error("Yetkili rolü bulunamadı: " + requiredRole);
            return;
        }

        // Kullanıcının role sahip olup olmadığını kontrol et
        if (user.hasRole(role)) {
            context.success();
        } else {
            // Rol yoksa, hata mesajını kullanıcıya göster
            Response errorResponse = context.form()
                    .setError("Uygulamaya erişim izniniz yok. Yönetici ile iletişime geçin. Gerekli Rol(Realm): "+requiredRole)
                    .createErrorPage(Response.Status.FORBIDDEN);
            context.failure(AuthenticationFlowError.ACCESS_DENIED, errorResponse);
            context.getEvent().error("Kullanıcı gerekli role sahip değil: " + requiredRole);
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Gerekirse ek aksiyonlar eklenebilir
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Gerekirse gerekli aksiyonlar eklenebilir
    }

    @Override
    public void close() {
        // Kaynakları temizle
    }
}