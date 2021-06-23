<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no" />
		<link rel="stylesheet" href="css/login-signup_style.css" />
		<link rel="stylesheet" href="css/aos.css" />
		<title>NORTHWEZ</title>
	</head>
	<body>
		<video autoplay muted loop id="myVideo">
			<source src="css/images/videobg.mp4" type="video/mp4" />
		</video>
		<div class="header_logo" data-aos="zoom-out">
			<a href="#"><img src="css/images/logo.png" alt="logo" /></a>
		</div>
		<nav role="navigation">
			<div id="menuToggle">
				<input type="checkbox" id="checkbox" />

				<span></span>
				<span></span>
				<span></span>

				<ul id="menu">
					<a href="#" onclick="login();revert();"><li>LOGIN</li></a>
					<a href="#" onclick="signup();revert();"><li>SIGN UP</li></a>
				</ul>
			</div>
		</nav>
		<section id="con1" class="container_1">
			<div class="container">
				<div class="logo" data-aos="zoom-out-down" data-aos-delay="500">
					<h1 data-aos="zoom-in">NORTHWEZ</h1>
					<h2>pushing beyond limitations</h2>
				</div>
				<a href="gallery.jsp" data-aos="zoom-out" data-aos-delay="1000">GALLERY</a>
			</div>
		</section>
		<section id="con2" class="container_2">
			<div class="container2">
                            <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
                                <h2>LOGIN</h2>
                                <label>
                                    <span>Username:</span>
                                    <br />
                                    <input type="text" id="uname" name="usernameLogin">
                                </label>
                                <label>
                                    <span>Password:</span>
                                    <br />
                                    <input type="password" id="pass" name="passwordLogin">
                                </label>
                                <input type="submit" value="ENTER" class="btn">
                            </form>
			</div>
		</section>
		<section id="con3" class="container_3">
                    <div class="container3">
                        <form
                            action="<%= request.getContextPath() %>/SignupServlet"
                            method="post"
                            autocomplete="off"
                        >
                        <h2>REGISTER</h2>
                        <label id="lbl1">
                            <input
                                    type="text"
                                    id="Fname"
                                    name="Fname"
                                    placeholder="First name"
                            />
                            <input
                                    type="text"
                                    id="Lname"
                                    name="Lname"
                                    placeholder="Last name"
                            />
                            </label>
                            <label>
                                <input
                                        type="text"
                                        id="username"
                                        name="usernameSignUp"
                                        placeholder="Username"
                                />
                            </label>
                            <label>
                                <input
                                        type="password"
                                        id="pass"
                                        name="passwordSignUp"
                                        placeholder="Password"
                                />
                            </label>
                            <label>
                                <input 
                                    type="email" 
                                    id="email" 
                                    name="email" 
                                    placeholder="E-mail" 
                                />
                            </label>
                            <label>
                                <input
                                        type="tel"
                                        id="conNumber"
                                        name="conNumber"
                                        max="11"
                                        placeholder="Contact number"
                                />
                            </label>
                            <label>
                                <input
                                        type="text"
                                        id="address"
                                        name="address"
                                        placeholder="Address"
                                />
                            </label>
                            <label>
                                <input
                                        type="text"
                                        id="tin"
                                        name="tin"
                                        placeholder="Tin Number"
                                />
                            </label>
                            <label>
                                <input
                                        type="text"
                                        id="company"
                                        name="company"
                                        placeholder="Company Name"
                                />
                            </label>
                            <label>
                                <img src="<%= request.getContextPath() %>/SimpleCaptchaServlet">

                                <input
                                        type="text"
                                        id="captcha"
                                        name="captcha"
                                        placeholder="Captcha Answer"
                                />
                            </label>
                            <input type="submit" value="ENTER" class="btn">
                        </form>
                    </div>
		</section>
		<div class="footer">
                    <a href="#">All Rights Resevered 2021</a>
		</div>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="js/aos.js"></script>
	<script>
		AOS.init({
			duration: 2000,
		});
	</script>
	<script type="text/javascript">
		function login() {
			var con1 = document.getElementById("con1");
			if ($(".container_1").hasClass("active")) {
				console.log("login");
			} else {
				con1.classList.add("active");
			}
			var con3 = document.getElementById("con3");
			if ($(".container_3").hasClass("active")) {
				con3.classList.remove("active");
			}
			var con2 = document.getElementById("con2");
			con2.classList.add("active");
		}
		function signup() {
			var con1 = document.getElementById("con1");
			if ($(".container_1").hasClass("active")) {
				console.log("register");
			} else {
				con1.classList.add("active");
			}
			var con2 = document.getElementById("con2");
			if ($(".container_2").hasClass("active")) {
				con2.classList.remove("active");
			}
			var con3 = document.getElementById("con3");
			con3.classList.add("active");
		}
		function revert() {
			var cbox = document.getElementById("checkbox");
			cbox.checked = !cbox.checked;
		}
	</script>
</html>
