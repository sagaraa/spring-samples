package com.okta.okta.spring.example.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("okta.oauth2")
public class AppProperties {
    private String issuer;
    private String audience;
    private String clientId;
    private String rolesClaim;
    @Value("${okta.client.org-url}")
    private String baseUrl;
    private String redirectUri;
    private String discoveryUri;
	public String getDiscoveryUri() {
		return discoveryUri;
	}
	public void setDiscoveryUri(String discoveryUri) {
		this.discoveryUri = discoveryUri;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRolesClaim() {
		return rolesClaim;
	}
	public void setRolesClaim(String rolesClaim) {
		this.rolesClaim = rolesClaim;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

    
}