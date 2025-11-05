<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
        <#-- Header vazio para não mostrar título padrão -->
    <#elseif section = "form">
    <div class="login-page-angular">
      
        <div class="form-section-angular">
          
            <div class="logo-container">
                <img src="${url.resourcesPath}/img/logo.avif" alt="Logo" onerror="this.style.display='none'" />
            </div>

            <h2 class="login-title-angular">Entre na sua conta</h2>

            <#if realm.password>
                <form id="kc-form-login" class="login-form-angular" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                    
                    <#if !usernameHidden??>
                        <div class="form-group login-field-angular">
                            <label for="username" class="login-label-angular">Email</label>
                            <input tabindex="1" 
                                   id="username" 
                                   class="form-control login-input-angular" 
                                   name="username" 
                                   value="${(login.username!'')}" 
                                   type="text" 
                                   autofocus 
                                   autocomplete="off"
                                   placeholder="example@gmail.com"
                                   aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                            <#if messagesPerField.existsError('username','password')>
                                <span class="error-message-angular">
                                    ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                </span>
                            </#if>
                        </div>
                    </#if>

                    <div class="form-group login-field-angular">
                        <label for="password" class="login-label-angular">Senha</label>
                        <input tabindex="2" 
                               id="password" 
                               class="form-control login-input-angular" 
                               name="password" 
                               type="password" 
                               autocomplete="off"
                               placeholder="Digite sua senha"
                               aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                        <#if usernameHidden?? && messagesPerField.existsError('username','password')>
                            <span class="error-message-angular">
                                ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                            </span>
                        </#if>
                    </div>

                    <#if realm.resetPasswordAllowed>
                        <div class="login-forgot-angular">
                            <a tabindex="5" href="${url.loginResetCredentialsUrl}" class="login-forgot-link-angular">
                                <span>Esqueceu a senha?</span>
                            </a>
                        </div>
                    </#if>
                    
                    <div class="btn-wrapper-angular">
                        <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                        <button tabindex="4" 
                                class="btn btn-primary login-button-angular" 
                                name="login" 
                                id="kc-login" 
                                type="submit">
                            Entrar
                        </button>
                    </div>
                </form>
            </#if>

            <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                <div class="btn-wrapper-angular-secondary">
                    <div class="divider">
                        <div></div>
                        <span>ou</span>
                        <div></div>
                    </div>

                    <div class="login-signup-angular">
                        <a tabindex="6" href="${url.registrationUrl}" class="btn btn-secondary login-signup-link-angular">
                            Se cadastrar
                        </a>
                    </div>
                </div>
            </#if>
            
        </div>
        
        <div class="main-section-angular">
            <div class="illustration">
                <img src="${url.resourcesPath}/img/main-ilustration.avif" alt="Ilustração" onerror="this.style.display='none'" />
            </div>
        </div>

    </div>
    </#if>
</@layout.registrationLayout>