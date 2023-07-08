import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.time.ZoneOffset.UTC;



@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

        private String initTime;
        @Override
        public void init() throws ServletException {
            initTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                    " yyyy-MM-dd    HH:mm:ss  "
            ));
        }


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/html; charset=utf-8");
            String utc = req.getParameter("timezone");

            if (utc == null) {
                    resp.getWriter().write(initTime);
            //        resp.setHeader("Refresh", "1");
                    resp.getWriter().close();
            } else {
                    utc = utc.toUpperCase();

                    if (!utc.contains("-") && !utc.contains("Z") ) {
                        if (utc.contains("UTC")) utc = (new StringBuilder(utc)).insert(3, "+").toString();
                        else utc = "+" + utc;
                    }

                    String dateTime = LocalDateTime.now(ZoneId.of(utc.replace(" ", ""))).format(DateTimeFormatter.ofPattern(
                            " yyyy-MM-dd    HH:mm:ss  "));

                    resp.getWriter().write("<p>The Date and time: ${dateTame}  UTC zone:  ${utc} </p>"
                            .replace("${dateTame}", dateTime)
                            .replace("${utc}", utc));
                    resp.getWriter().close();
            }
        }


        @Override
        public void destroy()  {
            initTime = null;
        }
}










