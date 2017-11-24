package com.machi.wajinga.ws.filters;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.mjinga.Mjinga;
import com.machi.wajinga.dao.mjinga.MjingaDao;
import com.machi.wajinga.ws.ServerConfiguration;

public class BasicAuthenticationFilter implements ContainerRequestFilter {

	public class SessionObject {
		public SessionObject(DateTime now, Mjinga mjinga_) {
			this.accessTime = now;
			this.mjinga = mjinga_;
		}

		public DateTime accessTime;
		public Mjinga mjinga;
	}

	/**
	 * 1 hour session time.
	 */
	private static final Integer SESSION_TIME = 60;
	private static final Map<String, SessionObject> SESSION = new ConcurrentHashMap<String, SessionObject>();

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource").type(MediaType.TEXT_PLAIN).build();

	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Forbidden to access this resource").type(MediaType.TEXT_PLAIN).build();

	@Context
	private ResourceInfo resourceInfo;
	private MjingaDao dao;
	private ServerConfiguration config;

	@Inject
	public BasicAuthenticationFilter(WajingaDao wajingaDao, ServerConfiguration config) {
		dao = wajingaDao.getMjingaDao();
		this.config = config;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();

		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
		}

		final MultivaluedMap<String, String> headers = requestContext.getHeaders();

		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		if ((authorization == null || authorization.isEmpty())) {
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}

		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));


		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		Mjinga mjinga_;
		String authorizationSid;

		if ("__sid__".equals(username)) {
			if (!SESSION.containsKey(password)) {
				if (method.isAnnotationPresent(PermitAll.class)) {
					return;
				}

				requestContext.abortWith(ACCESS_DENIED);
				return;
			}
			SessionObject session = SESSION.get(password);
			if (DateTime.now().minusMinutes(SESSION_TIME).isAfter(session.accessTime)) {
				SESSION.remove(password);
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			session.accessTime = DateTime.now();
			authorizationSid = password;
			mjinga_ = session.mjinga;
		} else {
			mjinga_ = dao.tafutaMjingaKwaJina(username);
			if (mjinga_ == null || !mjinga_.nywiraSahihi(password) || !mjinga_.anaruhusiwa()) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			authorizationSid = UUID.randomUUID().toString();
		}

		final Mjinga mjinga = mjinga_;
		
		/**
		 * Update access time
		 */
		dao.wekaMuda(mjinga);
		
		requestContext.setSecurityContext(new SecurityContext() {
			@Override
			public boolean isUserInRole(String role) {
				return mjinga.getCheo().name().equals(role);
			}

			@Override
			public boolean isSecure() {
				return config.isSecure();
			}

			@Override
			public Mjinga getUserPrincipal() {
				return mjinga;
			}

			@Override
			public String getAuthenticationScheme() {
				return config.getScheme();
			}
		});

		requestContext.setProperty("AuthorizationSid", authorizationSid);
		requestContext.setProperty("mjinga", mjinga.getJina());
		SESSION.put(authorizationSid, new SessionObject(DateTime.now(), mjinga));

		if (!method.isAnnotationPresent(RolesAllowed.class)) {
			return;
		}

		RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
		Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

		if (!isUserAllowed(mjinga, rolesSet)) {
			requestContext.abortWith(ACCESS_FORBIDDEN);
			return;
		}
	}

	private boolean isUserAllowed(Mjinga mjinga, final Set<String> roles) {
		return roles.contains(mjinga.getCheo().name());
	}

}
