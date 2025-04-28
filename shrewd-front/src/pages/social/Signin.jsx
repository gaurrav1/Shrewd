import { useForm } from "react-hook-form";
import { baseApi } from "../../backend-api/base";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../contexts/UserContext"; // <<< NEW

export function Signin() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();
  const { login } = useUser(); // <<< NEW

  const onSubmit = async (data) => {
    try {
      const csrfResponse = await baseApi.get("/auth/csrf/csrf-token");
      const csrfToken = csrfResponse.data.token;

      const loginResponse = await baseApi.post(
          "/auth/user/login",
          {
            email: data.email,
            password: data.password
          },
          {
            headers: {
              "X-Organization-ID": data.tenant,
              "X-XSRF-TOKEN": csrfToken
            }
          }
      );

      const { username, roles, tenantId, jwtToken } = loginResponse.data;

      login(
          { username, role: roles[0], tenantId },   // userData
          { jwtToken, csrfToken }                   // tokenData
      );

      // Redirect based on role
      if (roles.includes("ORGANIZATION")) {
        navigate("/admin-dashboard");
      } else if (roles.includes("SENIOR_DEV")) {
        navigate("/senior-dashboard");
      } else {
        navigate("/dashboard");
      }
    } catch (error) {
      console.error(error);
      alert("Login failed. Please check your credentials.");
    }
  };

  return (
      <div>
        <h2>Sign In</h2>
        <form onSubmit={handleSubmit(onSubmit)}>
          <input {...register("email", { required: true })} placeholder="Email" type="email" />
          {errors.email && <span>Email is required</span>}

          <input {...register("password", { required: true })} type="password" placeholder="Password" />
          {errors.password && <span>Password is required</span>}

          <input {...register("tenant", { required: true })} placeholder="Tenant ID" />
          {errors.tenant && <span>Tenant ID is required</span>}

          <button type="submit">Login</button>
        </form>
      </div>
  );
}