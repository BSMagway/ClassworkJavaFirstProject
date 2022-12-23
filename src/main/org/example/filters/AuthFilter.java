package org.example.filters;

import org.example.jdbc.dao.impl.RolesDAOImpl;
import org.example.model.Role;
import org.example.model.User;
import org.example.util.HttpServletUtil;
import org.example.util.IOUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    private static final Map<Role, List<String>> rolesMapping = new HashMap<>();
    private static final List<String> DEF_PATHS = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Set<Role> roles = new RolesDAOImpl().getAll();
       // GENERAL_USER : /home, /login
        roles.forEach(role -> {
            for( String line : IOUtils.readFile("D:\\itstep\\project\\Project\\src\\main\\webapp\\settings\\users_roles_mapping.txt").split("\n")){
                if(line.startsWith(role.getName())){
                    System.out.println("MAPPING FOUND FOR ROLE :" + role.getName());
                    List<String> paths = Arrays.asList(line.substring(line.indexOf(':') + 1).trim().split(", "));
                    rolesMapping.put(role,paths);  // /home, /login
                    if(role.getName().equals("GENERAL_USER")){
                        DEF_PATHS.addAll(paths);
                    }
                }
            }
        });
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest r = (HttpServletRequest)request;

        // get resource path
        String context = r.getContextPath();
        int index = r.getRequestURI().indexOf(context);
        String path = r.getRequestURI().substring( context.length());
        // get user from session
        User user = HttpServletUtil.getSessionUser(r);
        if(user == null){
            //not in session
            if(DEF_PATHS.contains(path)){
                System.out.println("Def");
                chain.doFilter(request, response);
                return;
            } else {
                RequestDispatcher rd = r.getRequestDispatcher("jsp/noAccess.jsp");
                rd.forward(request, response);
                return;
            }
        } else { // in session
            //TODO implement case when user is Active
            for(Role userRole: user.getRoles()){
                if(rolesMapping.get(userRole).contains(path)){
                    System.out.println("PASS: " + path);
                    chain.doFilter(request, response);
                    return;
                }
            }

            System.out.println("NO ACCESS FOR USER: " + user.getEmail() + ". PATH: " + path);
            r.setAttribute("msg2", "You ROLE is not suitable" );
            RequestDispatcher rd = r.getRequestDispatcher("jsp/msg.jsp");
            rd.forward(request, response);



        }




    }

    @Override
    public void destroy() {

    }
}
