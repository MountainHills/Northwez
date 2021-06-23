<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no" />
		<link rel="stylesheet" href="css/guest_style.css" />
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
                
                String title = (String)session.getAttribute("title");
                String progress = (String)session.getAttribute("progress");
                String newProgress = (String)session.getAttribute("newProgress");
                String updateProgress = (String)session.getAttribute("updateProgress");
                String username = (String)session.getAttribute("username");
            %>
            
            <div class="header_logo" data-aos="zoom-out">
                    <a href="#"><img src="css/images/logo.png" alt="logo" /></a>
            </div>
            <nav role="navigation">
                <div id="menuToggle" data-aos="fade-right">
                        <input type="checkbox" id="checkbox" />

                        <span></span>
                        <span></span>
                        <span></span>

                        <ul id="menu">
                                <a href="<%= request.getContextPath() %>/LogoutServlet"><li>LOGOUT</li></a>
                                <a href="gallery.jsp"><li>GALLERY</li></a>
                                <a href="#" onclick="show();revert();back();" id="show"
                                        ><li>REQUESTS</li></a
                                >
                                <%if(!progress.equalsIgnoreCase("ONGOING") && !progress.equalsIgnoreCase("PENDING") ){%>
                                    <a href="#" class="btn-book" onclick="revert();hide();"
                                            ><li>BOOK NOW</li></a
                                    >
                                <%}%>
                                
                                <%
                                    if(progress.equalsIgnoreCase("FINISHED"))
                                    {
                                %>
                                        <a href="<%= request.getContextPath() %>/RecordServlet"><li>RECORD</li></a>
                                <%
                                    }
                                %>
                        </ul>
                </div>
            </nav>
            <section id="modal-container">
                <div class="modal-background">
                    <div class="modal">
                        <form action="<%= request.getContextPath() %>/FormServlet" method="post">
                            <h2>BOOK A PROJECT NOW</h2>
                            <label>TITLE: </label><input type="text" name="titleForm">
                            <label>START: </label><input type="date" name="startForm">
                            <label>DEADLINE: </label><input type="date" name="deadlineForm">
                            <label>DESCRIPTION: </label><input type="text" name="descriptionForm">
                            <label>APPOINTMENT: </label><input type="datetime-local" name="appointmentForm">
                            <label>PRICE: </label><input type="number" name="priceForm">
                            <label></label><input type="Submit" value="ENTER">
                        </form>
                        <button id="back" class="back">Cancel</button>
                    </div>
                </div>
            </section>
            <section class="container_1 cont">
                    <div class="text-container" id="text-cont">
                            <h1>WELCOME <br /><span><%= (String)session.getAttribute("user") %></span></h1>
                            <!-- (username goes here) -->
                    </div>
                    <section class="boxes">
                            <div class="box-container box-1 inactive" id="box1">
                                    <img src='DisplayServlet.action' alt="" />
                                    <div class="box-text">
                                            <h1>Thumbnail goes here</h1>
                                    </div>
                                    <div class="waves">
                                            <!-- placeholder lang to -->

                                            <div class="wave1 position1"></div>
                                            <div class="wave2 position2"></div>
                                            <div class="wave3 position3"></div>
                                            <div class="wave4 position4"></div>
                                            <div class="wave5 position5"></div>
                                            <div class="wave6 position6"></div>
                                    </div>
                            </div>
                            <fieldset class="text inactive" id="box-text">
                                    <legend><%= progress %></legend>
                                    <h2><%= updateProgress %></h2>
                                    <h3>Project Title: <%= title %><span></span></h3>
                            </fieldset>
                    </section>
            </section>
            <div class="back-btn inactive" id="bck" onclick="hide()">BACK</div>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="js/aos.js"></script>
	<script>
		AOS.init({
			duration: 2000,
		});
	</script>
	<script>
		$(".btn-book").click(function () {
			$("#modal-container").removeClass("out").addClass("book");
			$("body").addClass("modal-active");
		});
		$("#back").click(function () {
			$("#modal-container").addClass("out");
			$("body").removeClass("modal-active");
		});

		function show() {
			var box1 = document.getElementById("box1");
			var text = document.getElementById("text-cont");
			var bck = document.getElementById("bck");
			if ($(".box-container").hasClass("inactive")) {
				box1.classList.remove("inactive");
				bck.classList.remove("inactive");
				$(".text").removeClass("inactive");
				text.classList.add("active");
				$("#modal-container").addClass("out");
				$("body").removeClass("modal-active");
			}
		}
		function hide() {
			var box1 = document.getElementById("box1");
			var text = document.getElementById("text-cont");
			var bck = document.getElementById("bck");
			if ($(".box-container").hasClass("inactive")) {
				console.log("yeet");
			} else {
				box1.classList.add("inactive");
				bck.classList.add("inactive");
				$(".text").addClass("inactive");
				text.classList.remove("active");
			}
		}
		function revert() {
			var cbox = document.getElementById("checkbox");
			cbox.checked = !cbox.checked;
		}
	</script>
</html>
