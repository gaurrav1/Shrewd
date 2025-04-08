import { useForm } from "react-hook-form"
import { useNavigate } from "react-router-dom";
import { baseApi } from "../backend-api/base";
import { jwtDecode } from "jwt-decode";
import { InputField } from "../components/InputField";
import { useState } from "react";

export function Signin() {
  const [jwtToken, setJwtToken] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const {register, handleSubmit, reset, formState: {errors}} = useForm({
    defaultValues: {
      email: "",
      password: "",
      tenant: ""
    }
  });

  const handleSuccessfulLogin = (token, decodedToken) => {
    const user = {
      username: decodedToken.sub,
      roles: decodedToken.roles ? decodedToken.roles.split(",") : [],
    };
    localStorage.setItem("JWT_TOKEN", token);
    localStorage.setItem("USER", JSON.stringify(user));

    navigate("/dashboard");
  };

  //function for handle login with credentials
  const onLoginHandler = async (data) => {
    try {
      setLoading(true);
      const {tenant, ...loginPayload} = data;
      console.log(tenant + loginPayload)
      const response = await baseApi.post("/auth/user/login", loginPayload, {headers: {"X-Organization-ID": tenant}});

      if (response.status === 200 && response.data.jwtToken) {
        setJwtToken(response.data.jwtToken);
        console.log(jwtToken);
        const decodedToken = jwtDecode(response.data.jwtToken);
        handleSuccessfulLogin(response.data.jwtToken, decodedToken);
        reset();
      } else {
        console.log("Failed to login")
      }
    } catch (error) {
      if (error) {
        console.log(error);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div>
        <h2>SignIn</h2>
        status: {loading}
      </div>
      <div>
        <form onSubmit={handleSubmit(onLoginHandler)}>
          <div>
            <InputField label="Tenant"
                required
                id="tenant"
                type="text"
                message="*Tenant is required"
                placeholder="type your tenant"
                register={register}
                errors={errors}/>
          </div>
          <div>
            <InputField label="email"
                required
                id="email"
                type="text"
                message="*email is required"
                placeholder="type your email"
                register={register}
                errors={errors}/>
          </div>
          <div>
            <InputField label="password"
                required
                id="password"
                type="password"
                message="*password is required"
                placeholder="type your password"
                register={register}
                errors={errors}/>
          </div>
          <button>Signin</button>
        </form>

      </div>
    </>
  )
}