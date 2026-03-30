document.getElementById("loginForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const data = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        const res = await fetch("/api/employee/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const token = await res.text();

       if (res.ok) {
           localStorage.setItem("token", token);

           // 🔥 simple role check
           if (data.email.includes("admin")) {
               window.location.href = "admin.html";
           } else {
               window.location.href = "dashboard.html";
           }
       }

    } catch (err) {
        document.getElementById("error").innerText = "Server error";
    }
});