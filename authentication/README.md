### **1. Make JwtUtils**

- Handles all JWT logic:

  - `secretKey` (for signing)
  - `validityInMs` (for token expiry)
  - `getSignedKey()` (returns a secure `Key` for signing/verification)
  - `createToken()` (builds JWT)
  - `validateToken()` (checks signature, expiry)
  - `getClaims()` (extracts info from token)

### **2. Implement UserDetails**

- Custom class (e.g. `CustomUserDetails`) that wraps your `User` entity and implements `UserDetails`.
- Tells Spring Security how to get username, password, roles.

### **3. Implement UserDetailsService**

- Loads your user from DB (via repository) for authentication.
- Returns `CustomUserDetails` for a given username.

### **4. Implement OncePerRequestFilter (JWT filter)**

- On every request:

  - Extract JWT from `Authorization` header.
  - Validate it.
  - If valid, set `UsernamePasswordAuthenticationToken` in `SecurityContext`.

- This allows Spring Security to treat the request as authenticated.

### **5. SecurityConfig**

- Central config for Spring Security:

  - `@Configuration` + `@EnableWebSecurity`
  - PasswordEncoder bean (usually BCrypt)
  - `filterChain` bean for customizing HTTP security
  - `authenticationManager` bean

- **NOTE:** You are right:
  If you provide `UserDetailsService` + `PasswordEncoder`, **Spring auto-configures a DaoAuthenticationProvider** for you.
- UserDetailsService loads user form db and uses passwordEncoder to match password

### **6. Use authenticationManager.authenticate() in login**

- In your login controller, call:

  ```java
  authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(username, password)
  );
  ```

- If successful, you can generate and return a JWT token.
