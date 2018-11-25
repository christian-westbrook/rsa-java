import java.util.Scanner;

public class RSA
{
    private int p, q, n, phi, e, d;
    Scanner input;

    public RSA()
    {
        input = new Scanner(System.in);
        generateKey();
        encryptDecryptMode();
    }

    public void generateKey()
    {
        String in;
        System.out.print("Please enter a large prime number: ");
        in = input.nextLine();
        p = Integer.parseInt(in);
        System.out.print("Please enter another large prime number: ");
        in = input.nextLine();
        q = Integer.parseInt(in);

        n = p * q;
        phi = (p - 1) * (q - 1);

        System.out.println("Phi(n) = " + phi);

        boolean validE = false;
        while(!validE)
        {
            System.out.print("Please enter a number e such that gcd(e, Phi(n)) = 1: ");
            in = input.nextLine();
            e = Integer.parseInt(in);

            int gcd = gcd(e, phi);
            if(gcd == 1)
                validE = true;
            else
                System.out.println("The gcd of " + e + " and " + phi + " is " + gcd + ".");
        }

        d = modularInverse(phi, e);
    }

    public void encryptDecryptMode()
    {
        boolean running = true;
        while(running)
        {
            System.out.println("Encrypt, decrypt, or exit?");

            String command = input.nextLine();

            if(command.equalsIgnoreCase("EXIT") || command.equalsIgnoreCase("CLOSE"))
                running = false;
            else if(command.charAt(0) == 'e' || command.charAt(0) == 'E')
                encrypt();
            else if(command.charAt(0) == 'd' || command.charAt(0) == 'D')
                decrypt();
        }

        input.close();
        System.exit(0);
    }

    public void encrypt()
    {
        boolean validInput = false;
        int b = -1;
        String in;

        while(!validInput)
        {
            System.out.print("Please enter a byte value to encrypt (0-255): ");
            in = input.nextLine();
            b = Integer.parseInt(in);

            if(b >= 0 && b <= 255)
                validInput = true;
            else
                System.out.println(b + " is not in the byte range of (0-255).");
        }

        long y = (long) Math.pow(b, e) % n;
        System.out.println("e(" + b + ") = " + y);
        System.out.println();
    }

    public void decrypt()
    {
        boolean validInput = false;
        int b = -1;
        String in;

        while(!validInput)
        {
            System.out.print("Please enter a byte value to decrypt (0-255): ");
            in = input.nextLine();
            b = Integer.parseInt(in);

            if(b >= 0 && b <= 255)
                validInput = true;
            else
                System.out.println(b + " is not in the byte range of (0-255).");
        }

        long x = (long) Math.pow(b, d) % n;
        System.out.println("d(" + b + ") = " + x);
        System.out.println();
    }

    public int gcd(int r1, int r2)
    {
        int r3;

        int i = 1;
        do
        {
            i++;
            r3 = r1 % r2;

            if(r3 != 0)
            {
                r1 = r2;
                r2 = r3;
            }
        } while (r3 != 0);

        return r2;
    }

    public int modularInverse(int r0, int r1)
    {
        if(r1 > r0)
        {
            int swap = r1;
            r1 = r0;
            r0 = swap;
        }

        int s0 = 1, s1 = 0, t0 = 0, t1 = 1, i = 1;
        int r2, s2, t2, q;

        do
        {
            i++;
            r2 = r0 % r1;
            q = (r0 - r2) / r1;
            s2 = s0 - (q * s1);
            t2 = t0 - (q * t1);

            if(r2 != 0)
            {
                r0 = r1;
                r1 = r2;
                s0 = s1;
                s1 = s2;
                t0 = t1;
                t1 = t2;
            }
        } while (r2 != 0);

        return t1;
    }

    public static void main(String[] args)
    {
        new RSA();
    }
}
