import java.util.Scanner;

public class HillCipher2x2 {
  private static Scanner in;
  public static void main(String[] args) {
    in = new Scanner(System.in);

    System.out.println("1. Enkripsi\n2. Dekripsi\nPilihan(1,2) : ");
    int pilihan = in.nextInt();
    in.nextLine();

    if(pilihan == 1) {
      System.out.println("---Enkripsi---");
      Enkripsi();
    } else if(pilihan == 2) {
      System.out.println("---Dekripsi---");
      Dekripsi();
    } else {
      System.out.println("Pilihan Invalid");
    }
  }

  private static void Dekripsi() {
    System.out.print("Masukkan teks : ");
    String msg = in.nextLine();
    msg = msg.replaceAll("\\s" , "");
    msg = msg.toUpperCase();

    // jika panjang (length) dari teks tidak beraturan maka jalankan padding
    int lenCek = 0;
    if (msg.length() % 2 != 0) {
      msg += "0";
      lenCek = 1;
    }

    // teks dijadikan matriks
    int[][] msg2D = new int[2][msg.length()];
    int itr1 = 0;
    int itr2 = 0;
    for (int i = 0; i < msg.length(); i++) {
      if (i%2 == 0) {
        msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
        itr1++;
      } else {
        msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
        itr2++;
      } // if-else
    } // for

    System.out.print("Masukkan matriks kunci : ");
    String key = in.nextLine();
    key = key.replaceAll("\\s","");
    key = key.toUpperCase();

    // kunci matriks 2x2
    int[][] key2D = new int[2][2];
    int itr3 = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        key2D[i][j] = (int)key.charAt(itr3) - 65;
        itr3++;
      }
    }

    // Memvalidasi kunci matriks dan menemukan determinan matriks
    int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
    deter = modulfunc(deter, 26);

    // invers perkalian kunci matriks
    int inv = -1;
    for (int i = 0; i < 26; i++) {
      int tempInv = deter * i;
      if (modulfunc(tempInv, 26) == 1) {
        inv = i;
        break;
      } else {
        continue;
      } // if-else
    } // for

    // adjoint matriks dan
    int swapTemp = key2D[0][0];
    key2D[0][0] = key2D[1][1];
    key2D[1][1] = swapTemp;

    // Mengubah tanda
    key2D[0][1] *= -1;
    key2D[1][0] *= -1;
    
    key2D[0][1] = modulfunc(key2D[0][1], 26);
    key2D[1][0] = modulfunc(key2D[1][0], 26);

    // Kuadratkan inverse perkalian dengan adjoint matriks
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        key2D[i][j] = inv;
      }
    }
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        key2D[i][j] = modulfunc(key2D[i][j], 26);
      }
    }

    String dekripteks = "";
    int itrCount = msg.length() / 2;
    if (lenCek == 0) {
      // jika panjang teks yang dimasukkan % 2 = 0
      for (int i = 0; i < itrCount; i++) {
        int temp1 = msg2D[0][i] * key2D[0][0] + key2D[1][i] * key2D[0][1];
        dekripteks += (char)((temp1 % 26) + 65);
        int temp2 = msg2D[0][i] * key2D[1][0] + key2D[1][i] * key2D[1][1];
        dekripteks += (char)((temp2 % 26) + 65);
      }
    } else {
      // jika panjang teks yang dimasukkan % 2 = 0 (tidak beraturan lengthnya)
      for (int i = 0; i < itrCount-1; i++) {
        int temp1 = msg2D[0][i] * key2D[0][0] + key2D[1][i] * key2D[0][1];
        dekripteks += (char)((temp1 % 26) + 65);
        int temp2 = msg2D[0][i] * key2D[1][0] + key2D[1][i] * key2D[1][1];
        dekripteks += (char)((temp2 % 26) + 65);
      }
    }

    System.out.println("Plainteks : " + dekripteks);  
  }

  private static void Enkripsi() {
    System.out.print("Masukkan teks : ");
    String msg = in.nextLine();
    msg = msg.replaceAll("\\s" , "");
    msg = msg.toUpperCase();

    // jika panjang (length) dari teks tidak beraturan maka jalankan padding
    int lenCek = 0;
    if (msg.length() % 2 != 0) {
      msg += "0";
      lenCek = 1;
    }

    // teks dijadikan matriks
    int[][] msg2D = new int[2][msg.length()];
    int itr1 = 0;
    int itr2 = 0;
    for (int i = 0; i < msg.length(); i++) {
      if (i%2 == 0) {
        msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
        itr1++;
      } else {
        msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
        itr2++;
      } // if-else
    } // for

    // // Kemudian test 2D matriksnya sudah bekerja atau belum
    // for (int i = 0; i < 2; i++) {
    //   for (int j = 0; j < msg.length(); j++) {
    //     System.out.print((char)(msg2D[i][j]+65) + "");
    //   }
    //   System.out.println();
    // }

    System.out.print("Masukkan matriks kunci : ");
    String key = in.nextLine();
    key = key.replaceAll("\\s","");
    key = key.toUpperCase();

    // kunci matriks 2x2
    int[][] key2D = new int[2][2];
    int itr3 = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        key2D[i][j] = (int)key.charAt(itr3) - 65;
        itr3++;
      }
    }

    // Memvalidasi kunci matriks dan menemukan determinan matriks
    int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
    deter = modulfunc(deter, 26);

    // invers perkalian kunci matriks
    int inv = -1;
    for (int i = 0; i < 26; i++) {
      int tempInv = deter * i;
      if (modulfunc(tempInv, 26) == 1) {
        inv = i;
        break;
      } else {
        continue;
      } // if-else
    } // for

    if (inv == -1) {
      System.out.println("Kunci Invalid");
      System.exit(1);
    }

    String enkripteks = "";
    int itrCount = msg.length() / 2;
    if (lenCek == 0) {
      // jika panjang teks yang dimasukkan % 2 = 0
      for (int i = 0; i < itrCount; i++) {
        int temp1 = msg2D[0][i] * key2D[0][0] + key2D[1][i] * key2D[0][1];
        enkripteks += (char)((temp1 % 26) + 65);
        int temp2 = msg2D[0][i] * key2D[1][0] + key2D[1][i] * key2D[1][1];
        enkripteks += (char)((temp2 % 26) + 65);
      }
    } else {
      // jika panjang teks yang dimasukkan % 2 = 0 (tidak beraturan lengthnya)
      for (int i = 0; i < itrCount-1; i++) {
        int temp1 = msg2D[0][i] * key2D[0][0] + key2D[1][i] * key2D[0][1];
        enkripteks += (char)((temp1 % 26) + 65);
        int temp2 = msg2D[0][i] * key2D[1][0] + key2D[1][i] * key2D[1][1];
        enkripteks += (char)((temp2 % 26) + 65);
      }
    }

    System.out.println("Ciphertext : " + enkripteks);
  }

  // modul function
  public static int modulfunc(int a, int b) {
    int result = a % b;
    if (result < 0) {
      result += b;
    }
    return result;
  }
}