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
public class VecBitGenerico {

    int v[];
    int cant;
    int numBit;

    public VecBitGenerico(int cant, int numBit) {
        int Nbit = cant * numBit;
        int NE = Nbit / 32;
        if (Nbit % 32 != 0) {
            NE++;
        }
        v = new int[NE];
        this.cant = cant;
        this.numBit = numBit;
    }

    private int nBit(int pos) {
        return ((pos - 1) * this.numBit % 32) + 1;
    }

    private int nEnt(int pos) {
        return ((pos - 1) * this.numBit / 32);
    }

    public void insertar(int pos, int dato) {
        if ((pos <= cant) && (pos > 0)) {
            int valor = dato;
            int nBit = nBit(pos);
            int nEnt = nEnt(pos);
            int mask = (int) Math.pow(2, this.numBit) - 1;
            mask = mask << (nBit - 1);
            mask = ~mask;
            v[nEnt] = v[nEnt] & mask;
            dato = dato << (nBit - 1);
            v[nEnt] = v[nEnt] | dato;
            if (nBit - 1 + this.numBit > 32) {
                int nBitFin = nBit - 1 + this.numBit - 32;
                int mask1 = (int) Math.pow(2, nBitFin) - 1;
                mask1 = ~mask1;
                v[nEnt + 1] = v[nEnt + 1] & mask1;
                valor = valor >>> (this.numBit - nBitFin);
                v[nEnt + 1] = v[nEnt + 1] | valor;
            }
        }
    }

    public int sacar(int pos) {
        int mask = (int) Math.pow(2, this.numBit) - 1;
        int nBit = nBit(pos);
        int nEnt = nEnt(pos);
        mask = mask << (nBit - 1);
        mask = mask & v[nEnt];
        mask = mask >>> (nBit - 1);
        if (nBit - 1 + this.numBit > 32) {
            int nBitFin = nBit - 1 + this.numBit - 32;
            int mask1 = (int) Math.pow(2, nBitFin) - 1;
            mask1 = mask1 & v[nEnt + 1];
            mask1 = mask1 << (this.numBit - nBitFin);
            mask = mask | mask1;
        }
        return mask;
    }

    @Override
    public String toString() {
        String s = "v=[";
        for (int i = 1; i <= cant; i++) {
            s = s + sacar(i) + ",";
        }
        return s + "]";
    }

    public static void main(String[] args) {
        VecBitGenerico x = new VecBitGenerico(3, 3);
        for (int i = 1; i <= 2; i++) {
            x.insertar(i, i);
        }
        String d = x.toString();
        System.out.println(d);
    }
}
