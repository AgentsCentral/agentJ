//package ai.agentscentral.http.filter;
//
//import ai.agentscentral.http.auth.Authorizer;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//import static ai.agentscentral.http.json.Jsonify.asJson;
//import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
//
///**
// * AgentJAuthorizationFilter
// *
// * @author Rizwan Idrees
// */
//public class AgentJAuthorizationFilter implements Filter {
//
//    private static final String CONTENT_TYPE = "application/json";
//    private static final UnAuthorizedResponse DEFAULT_UNAUTHORIZED_RESPONSE =
//            new UnAuthorizedResponse("You are not authorized. Please contact the support team");
//
//    private final List<Authorizer> authorizers;
//
//    public AgentJAuthorizationFilter(List<Authorizer> authorizers) {
//        this.authorizers = authorizers;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain) throws ServletException, IOException {
//
//        final boolean isAnyUnAuthorized = authorizers.stream()
//                .anyMatch(authorizer -> !authorizer.isAuthorized((HttpServletRequest) servletRequest));
//
//        if (isAnyUnAuthorized) {
//            sendUnAuthorizedResponse((HttpServletResponse) servletResponse);
//            return;
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    private void sendUnAuthorizedResponse(HttpServletResponse response) {
//        response.setContentType(CONTENT_TYPE);
//        response.setStatus(SC_UNAUTHORIZED);
//        try (PrintWriter writer = response.getWriter()) {
//            writer.write(asJson(DEFAULT_UNAUTHORIZED_RESPONSE));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private record UnAuthorizedResponse(String message) {
//    }
//}
