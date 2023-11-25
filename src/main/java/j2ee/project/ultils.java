package j2ee.project;

public class ultils {

    public static Boolean isValidToken(String token) {
        //check token is valid
        if (token == null) {
            return false;
        }
        //check token is valid
        if (token.length() < 10) {
            return false;
        }
        //check token is valid
        String time = token.substring(10);
        long timeLong = Long.parseLong(time);
        long currentTime = System.currentTimeMillis();
        if (currentTime - timeLong > 86400000) {
            //1 day
            return false;
        }
        return true;
    }
}
