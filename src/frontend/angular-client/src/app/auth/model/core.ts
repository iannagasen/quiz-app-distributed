export interface AuthenticationRequest {
  email: string
  password: string 
}

export interface AuthenticationResponse {
  timestamp: string;
  message: string;
  status: string;
  data: any
}