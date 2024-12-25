//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//public class MyHandler implements AuthenticationSuccessHandler {
//    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
//
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication auth) {
//        if (hasTemporaryPassword(auth)) {
//            response.sendRedirect("/changePassword");
//        } else {
//            target.onAuthenticationSuccess(request, response, auth);
//        }
//    }
//
//    public void proceed(HttpServletRequest request,
//                        HttpServletResponse response, Authentication auth) {
//        target.onAuthenticationSuccess(request, response, auth);
//    }
//}
//
//@Controller("/changePassword")
//public class ChangePasswordController {
//
//    @Autowired
//    private MyHandler handler;
//
//    @RequestMapping(method = POST)
//    public void changePassword(HttpServletRequest request,
//                               HttpServletResponse response,
//                               @RequestParam(name = "newPassword") String newPassword) {
//
//        // handle password change
//
//
//        // proceed to the secured page
//        handler.proceed(request, response, auth);
//    }
//
//    // form display method, etc
//
//}