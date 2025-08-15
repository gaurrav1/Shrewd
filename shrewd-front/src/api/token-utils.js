// This function reads directly from context when available
export function getUserTokens() {
    try {
        const context = JSON.parse(sessionStorage.getItem('user-tokens')) || {};
        return context;
    } catch {
        return {};
    }
}
