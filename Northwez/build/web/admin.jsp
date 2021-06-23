<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.SectionRecords, java.util.*"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/admin_style.css" />
        <link rel="stylesheet" href="css/aos.css" />
        <title>NORTHWEZ</title>
    </head>
    <body>
    <% 
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 

        if(session.getAttribute("user") == null)
        {
            response.sendRedirect("/Northwez/login-signup.jsp");
        }

        String[][] clientNames = SectionRecords.getClientNames();
        String[][] requestSection = SectionRecords.getRequestSection(); 
        String[][] ongoingSection = SectionRecords.getOngoingSection();
        String[][] finishedSection  = SectionRecords.getFinishedSection();
    %>

        <div class="header_logo" data-aos="zoom-out">
                <a href="#"><img src="css/images/logo.png" alt="logo" /></a>
        </div>
        <nav role="navigation">
            <div id="menuToggle">
                <input type="checkbox" id="checkbox" />

                <span></span>
                <span></span>
                <span></span>

                <!-- Allows you to logout and download the records. -->
                <ul id="menu">
                    <a href="<%= request.getContextPath() %>/LogoutServlet"><li>LOGOUT</li></a>
                    <a href="<%= request.getContextPath() %>/RecordServlet"><li>RECORD</li></a>
                </ul>
            </div>
        </nav>
                        
        <section class="main-container">
            <div class="new-requests">
                <fieldset id="req">
                    <legend>REQUESTS</legend>

                    <!-- This is the start of the single instance. -->

                    <% for (int i = 0; i < requestSection[0].length; i++)
                    {
                        int foreignKey = Integer.parseInt(requestSection[1][i]) - 1; %>
                    
                        <div class="req-box">
                            <div class="req-box-text">
                                <h1><% out.print(requestSection[2][i]); %></h1>
                                <h3><b>client</b><% out.print(clientNames[0][foreignKey] + " " + clientNames[1][foreignKey]); %></h3>
                                <h3><b>topic</b><% out.print(requestSection[3][i]); %></h3>
                                <h3><b>timeframe</b><% out.print(requestSection[4][i] + " - " + requestSection[5][i]); %></h3>
                                <h3><b>appointment</b><% out.print(requestSection[6][i]); %></h3>
                                <h3><b>proposed price</b><% out.print(requestSection[7][i]); %></h3>
                            </div>
                            
                            <div class="choices-1">
                                <div class="buttons">
                                    <form autocomplete="off" action="AdminServlet" method="POST">
                                        <!-- These are important hidden fields. They make the requests distinct.-->
                                        <input type="hidden" name="order_id" value="<% out.print(requestSection[0][i]); %>">
                                        <input type="hidden" name="section" value="request">
                                        
                                        <!-- you can wrap this in a form and make the buttons input type=submit -->
                                        <div class="btn">
                                            <input type="submit" name="decision" value="accept" class="button accept" />
                                            <input type="submit" name="decision" value="reject" class="button reject" />
                                        </div>
                                        
                                        <!-- These fields needs to be filled up. Especially, the price. Else, it would be nullPointerException.-->
                                        <input
                                            type="text"
                                            name="final-start"
                                            id="finalDate"
                                            placeholder="START(dd/mm/yyyy)"
                                        />
                                        <input
                                            type="text"
                                            name="final-deadline"
                                            id="finalDate"
                                            placeholder="DEADLINE(dd/mm/yyyy)"
                                        />
                                        <input
                                            type="text"
                                            name="final-price"
                                            id="finalPrice"
                                            placeholder="PRICE:                "
                                        />
                                    </form>
                                </div>
                            </div>
                        </div> 
                    <% } %>   
                    
                    <!-- This is the end of the single instance. -->
                </fieldset>
            </div>

                <!-- The start of the ONGOING PROJECTS SECTION-->
            <div class="ongoing">
                <fieldset>
                    <legend>ONGOING</legend>

                    <!-- The start of the single instance of an ongoing project-->
                    
                    <% 
                        for (int i = 0; i < ongoingSection[0].length; i++) { 
                        SectionRecords.setOrderID(Integer.parseInt(ongoingSection[0][i]));
                        int orderId = SectionRecords.getOrderID();
                        session.setAttribute("orderId", orderId);
                        session.setAttribute("sectionType","ONGOING");
                    %>
                        <div class="ongoing-box">
                            <div class="ongoing-box-text">
                                <h3 style="line-height: 1px;font-family: 'Alegreya Sans SC', sans-serif"><%out.print(ongoingSection[2][i]);%></h3>
                                <h1>Progress:</h1>
                                <form id="update" action="AdminServlet" method="POST" enctype="multipart/form-data">
                                    <!-- These are important hidden fields. They make the requests distinct.-->
                                    <input type="hidden" name="order_id" value="<% out.print(ongoingSection[0][i]); %>">
                                    <input type="hidden" name="section" value="ongoing">
                                    
                                    <!-- Set the progress of the project. Default would be ONGOING.-->
                                    <div class="selectProgess">
                                        <select list="progress"name="progress" placeholder="Ongoing" class="list"/>
                                            <option value="ongoing">Ongoing</option>
                                            <option value="finished">Finished</option>
                                            <option value="cancelled">Cancelled</option>
                                        </select>
                                    </div>
                                    <!--<center><label for="file" class="submit">Upload Thumbnail</label></center>-->
                                    <input name="file" id="file"  type="file" class="upload">
                                    <!-- The UPDATEPROGRESS would be display here.-->
                                    <h2>
                                        Description:
                                        <textarea
                                            name="updateDescription"
                                            style="resize: none"
                                        ><% out.print(ongoingSection[1][i]); %></textarea>
                                    </h2>
                                    <input type="submit" value="UPDATE" class="submit" />
                                </form>
                            </div>
                            <!-- This still needs work. This is temporary -->
                            <img src="DisplayServlet.action?orderId=${orderId}" class="img" alt="" />
                        </div>
                    <% } %>
                    
                    <!-- This is the end of the single instance.-->
                </fieldset>
            </div>

                <!-- The Start of the FINISHED Section!-->
                <div class="completed">
                        <fieldset>
                                <legend>FINISHED</legend>

                                <!-- The first instance of a finished Project.-->
                                
                                <% for (int i = 0; i < finishedSection[0].length; i++) { 
                                    SectionRecords.setOrderID(Integer.parseInt(finishedSection[1][i]));
                                    int orderId = SectionRecords.getOrderID();
                                    session.setAttribute("sectionType","FINISHED");
                                    session.setAttribute("orderId", orderId);
                                %>
                                    <div class="completed-box">
                                            <div class="completed-box-text"><h1><% out.print(finishedSection[0][i]); %></h1></div>
                                            <img src="DisplayServlet.action?orderId=${orderId}" class ="img" alt=""/>
                                    </div>
                                <% } %>
                                
                                <!-- The end of the first instance -->
                        </fieldset>
                </div>
        </section>
    </body>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="js/aos.js"></script>
    <script>
            AOS.init({
                    duration: 2000,
            });

            function revert() {
                    var cbox = document.getElementById("checkbox");
                    cbox.checked = !cbox.checked;
            }

            $(".req-box").hover(function () {
                    $(this).find(".req-box-text").toggleClass("active");
                    $(this).find(".req-box-text").toggleClass("finished").delay(200);
                    $(this).find(".choices-1").toggleClass("active");
            });

            $(".ongoing-box").hover(function () {
                    $(this).find(".img").toggleClass("active");
            });
            $(".completed-box").hover(function () {
                    $(this).find(".img").toggleClass("active");
            });
    </script>
</html>
