package web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.faces.util.LRUMap;
import entity.payload.ActorUserJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.enumeration.CompanyRole;
import util.security.JWTManager;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    private JWTManager jWTManager;

    public SecurityFilter() {
        jWTManager = new JWTManager();
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SecurityFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SecurityFilter:DoAfterProcessing");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (debug) {
            log("SecurityFilter:doFilter()");
        }

        doBeforeProcessing(request, response);
        Throwable problem = null;
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String pathInfo = httpRequest.getPathInfo();

            Map<String, String> headerMap = new HashMap<>();

            // intercept to check what is the WS called
            if (!pathInfo.contains("/GenericResource")) {
                Enumeration<String> headerNames = httpRequest.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String key = (String) headerNames.nextElement();
                        String value = httpRequest.getHeader(key);
                        System.out.println(key);
                        headerMap.put(key, value);
                    }
                }

                // check if the api put bearer at header
                if (headerMap.containsKey("authorization")) {
                    // check the JWT token
                    String JWT = headerMap.get("authorization");
                    String []splitJWT = JWT.split(" ");
                    JWT = splitJWT[1];
                    System.out.println("*** " + JWT);

                    try {
                        System.err.println(JWT);
                        Claims claims = jWTManager.decodeJWT(JWT);
                        claims.getExpiration();

//                        System.out.println("Expiry: " + claims.getExpiration());
//                        Map<String, Object> claimsMap = (Map<String, Object>)claims;
//                        // get body payload
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        
//                        Object LoggedInUserObject = claimsMap.get("LoggedInUser");
//                        LinkedHashMap<String, String> LoggedInUserMap = new LinkedHashMap<>();
//                        LoggedInUserMap = objectMapper.convertValue(LoggedInUserObject, LoggedInUserMap.getClass());
//                        
//                        //    public ActorUserJWT(String email, String firstName, String lastName, CompanyRole role) {
//                        ActorUserJWT actorUserJWT = new ActorUserJWT(LoggedInUserMap.get("email"), LoggedInUserMap.get("firstName"), LoggedInUserMap.get("lastName"), CompanyRole.valueOf(LoggedInUserMap.get("role")));
//                        System.out.println(actorUserJWT.getEmail() + "-" + actorUserJWT.getFirstName() + "-" + actorUserJWT.getLastName() + "-" + actorUserJWT.getRole());
                    } catch (ExpiredJwtException | SignatureException exception) {
                        httpResponse.sendError(401, exception.getMessage());
                        return;
                    }

                } else {
                    // no JWT token (unauthrised)
                    httpResponse.sendError(406);
                    return;
                }

//                httpResponse.sendError(401);
//                return;
            }

            chain.doFilter(request, response);
        } catch (IOException | ServletException t) {
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("SecurityFilter:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SecurityFilter()");
        }
        StringBuffer sb = new StringBuffer("SecurityFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
