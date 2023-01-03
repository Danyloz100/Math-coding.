
public class DiffieHellman {
    static long calculatePower(long x, long y, long P)
    {
        long result = 0;
        if (y == 1){
            return x;
        }
        else{
            result = ((long)Math.pow(x, y)) % P;
            return result;
        }
    }
}
