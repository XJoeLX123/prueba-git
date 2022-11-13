/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Dell
 */
public class Alarma {

    VecBitGenerico a;
    String vn[];
    int dim;

    public Alarma(int cant) {
        a = new VecBitGenerico(cant, 19);
        vn = new String[cant];
        dim = 0;
    }
    public int dimension(){
        return dim;
    }
    public void insertar(String Nombre, int hora, int minuto, int dia, int repetir) {
        vn[dim] = Nombre;
        dim++;
        //hora=5 minuto=6 dia=7 repetir=1
        int z = hora;
        z = (z << 6) | minuto;
        z = (z << 7) | dia;
        z = (z << 1) | repetir;
        a.insertar(dim, z);
    }

    public int getHora(int pos) {
        //hora=5 minuto=6 dia=7 repetir=1
        return a.sacar(pos) >>> 14;
    }

    public int getMinuto(int pos) {
        //hora=5 minuto=6 dia=7 repetir=1
        int z = a.sacar(pos);
        z = (z >>> 8) & ((int) Math.pow(2, 6) - 1);
        return z;
    }

    public int getDia(int pos) {
        //hora=5 minuto=6 dia=7 repetir=1
        int z = a.sacar(pos);
        z = (z >>> 1) & ((int) Math.pow(2, 7) - 1);
        return z;
    }

    public int getRepetir(int pos) {
        return a.sacar(pos) & 1;
    }

    public String getNombre(int pos) {
        return vn[pos - 1];
    }

    public String mostrarDia(int pos) {
        String s = "";
        int x= getDia(pos);
        int dia;
        for (int i = 0; i < 7; i++) {
            int mask=(1 << i);
             dia = x & mask;
            if (dia != 0) {
                switch (i) {
                    case 0:
                        s=s+"\n"+"- "+"Lunes";
                        break;
                    case 1:
                        s=s+"\n"+"- "+"Martes";
                        break;
                    case 2:
                        s=s+"\n"+"- "+"Miercoles";
                        break;
                    case 3:
                        s=s+"\n"+"- "+"Jueves";
                        break;
                    case 4:
                        s=s+"\n"+"- "+"Viernes";
                        break;
                    case 5:
                        s=s+"\n"+"- "+"Sabado";
                        break;
                    case 6:
                        s=s+"\n"+"- "+"Domingo";
                        break;
                }
            }
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 1; i <= dim; i++) {
            String rep=(getRepetir(i)==1)?"si":"no";
            s = s + "Nombre de Alarma: " + getNombre(i) + "\n"
                    + "Hora: " + getHora(i) + " : " + getMinuto(i) + "\n"
                    + "Dia: "+mostrarDia(i)+"\n"
                    +"Repetir: "+rep+"\n\n";
        }
        return s;
    }

}
