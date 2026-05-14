package util;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

    private static final String LANG_PARAM = "lang";
    private static final String DEFAULT_LANG = "es";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);

        String langParam = httpRequest.getParameter(LANG_PARAM);

        if (langParam != null && !langParam.isBlank()) {
            session.setAttribute("lang", langParam);
        }

        String langSession = (String) session.getAttribute("lang");

        if (langSession == null || langSession.isBlank()) {
            langSession = DEFAULT_LANG;
            session.setAttribute("lang", langSession);
        }

        Locale locale = Locale.forLanguageTag(langSession);

        request.setAttribute("lang", langSession);
        request.setAttribute("locale", locale);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
