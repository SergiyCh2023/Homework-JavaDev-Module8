import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.net.http.HttpRequest;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class TimezoneValidateFilter extends HttpFilter {
    String utc;
    List<String> timeZones;

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws ServletException, IOException {

        utc = req.getParameter("timezone");

        timeZones = Arrays.asList("-12","-11","-10","-9","-8","-7","-6","-5", "-4", "-3", "-2", "-1", "Z",
                "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10", "+11", "+12", "+13", "+14",
                "UTC-12","UTC-11","UTC-10","UTC-9","UTC-8","UTC-7","UTC-6","UTC-5", "UTC-4", "UTC-3", "UTC-2", "UTC-1", "UTCZ",
                "UTC+1", "UTC+2", "UTC+3", "UTC+4", "UTC+5", "UTC+6", "UTC+7", "UTC+8", "UTC+9", "UTC+10", "UTC+11", "UTC+12", "UTC+13", "UTC+14");



            if (utc == null)   chain.doFilter(req, res);
            else if ( !check(utc)) {
                res.setStatus(400);

                res.setContentType("text/html; charset=utf-8");
                res.getWriter().write("Invalid timezone <br>");
    //            res.getWriter().write("<p> UTC timezone :${utc}</p>".replace("${utc}", utc));
                res.getWriter().close();
            } else     chain.doFilter(req, res);
        }


        private boolean check (String utc) {
            boolean result = false;

                utc = utc.toUpperCase();
                if (!utc.contains("-") && !utc.contains("Z")) {
                    if (utc.contains("UTC")) utc = (new StringBuilder(utc.replace(" ", ""))).insert(3, "+").toString();
                    else utc = "+" + utc.replace(" ", "");
                }

                for (String current: timeZones) {
                    if (current.equals(utc)) result = true;
                }

                return result;

        }


    }






