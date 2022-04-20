package com.emesall.petclinic.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();

	WebInvocationPrivilegeEvaluator privilegeEvaluator;

	@Autowired
	@Lazy
	public void setPrivilegeEvaluator(WebInvocationPrivilegeEvaluator privilegeEvaluator) {
		this.privilegeEvaluator = privilegeEvaluator;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			String rootBase = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
			targetUrl = targetUrl.split(rootBase)[1];

			if (targetUrl != null) {
				clearAuthenticationAttributes(request);

				if (privilegeEvaluator.isAllowed(targetUrl, authentication)) {
					getRedirectStrategy().sendRedirect(request, response, targetUrl);
				} else {
					targetUrl = "/";
					getRedirectStrategy().sendRedirect(request, response, targetUrl);
				}

				// Use the DefaultSavedRequest URL

			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}

		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

}
