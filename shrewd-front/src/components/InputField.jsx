export const InputField = ({ label, id, type, errors, register, required,
            message, className, min, value, autoFocus, placeholder, readOnly,
    }) => {
    return (
        <div>
            <label htmlFor={id}>
                {label}
            </label>

            <input
                type={type}
                id={id}
                placeholder={placeholder}
                {...register(id, {
                    required: { value: required, message },
                    minLength: min
                        ? { value: min, message: "Minimum 6 character is required" }
                        : null,
                })}
                readOnly={readOnly}
            />

            {errors[id]?.message && (
                <p>
                    {errors[id]?.message}*
                </p>
            )}
        </div>
    );
};
