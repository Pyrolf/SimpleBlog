export class JwtAutResponse {
    authenticationToken!: string;
    refreshToken!: string;
    expiresAt!: Date;
    username!: string;
}