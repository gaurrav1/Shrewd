import { useForm } from "react-hook-form";
import { baseApi } from "../../backend-api/base";
import { useNavigate } from "react-router-dom";

export function Signup() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await baseApi.post("/auth/organization/register", data);
      alert("Organization registered successfully.");
      navigate("/signin");
    } catch (error) {
      console.error(error);
      alert("Registration failed.");
    }
  };

  return (
      <div className="signup">
        <h2>Sign Up</h2>
        <form onSubmit={handleSubmit(onSubmit)}>
          <input {...register("org_name", { required: true })} placeholder="Organization Name" />
          {errors.org_name && <span>Organization Name is required</span>}

          <input {...register("username", { required: true })} placeholder="Username" />
          {errors.username && <span>Username is required</span>}

          <input {...register("email", { required: true })} placeholder="Email" type="email" />
          {errors.email && <span>Email is required</span>}

          <input {...register("phone", { required: true })} placeholder="Phone" />
          {errors.phone && <span>Phone is required</span>}

          <input {...register("address", { required: true })} placeholder="Address" />
          {errors.address && <span>Address is required</span>}

          <input {...register("password", { required: true })} type="password" placeholder="Password" />
          {errors.password && <span>Password is required</span>}

          <input {...register("tenant", { required: true })} placeholder="Tenant ID" />
          {errors.tenant && <span>Tenant ID is required</span>}

          <button type="submit">Register</button>
        </form>
      </div>
  );
}
