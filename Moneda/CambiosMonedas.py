from tkinter import *
from tkinter.ttk import Notebook
from tkinter import messagebox as MessageBox
import Util

iconos = ["./iconos/Grafica.png","./iconos/Datos.png",]
textos = ["Grafica Fecha vs Cambio","Calculas Estadisticas"]

def mostrarGrafica():
    MessageBox.showinfo("Grafica", "Hizo click en grafica")

def mostrarEstadisticas():
    MessageBox.showinfo("Estadisticas", "Hizo click en estadisticas")

ventana = Tk()
ventana.title("Cambio de Monedas")
ventana.geometry("400x300")

botones = Util.agregarBarra(ventana, iconos, textos)
botones[0].config(command=mostrarGrafica)
botones[1].config(command=mostrarEstadisticas)

Util.agregarBarra(ventana, iconos, textos)
frmMoneda = Frame(ventana)
frmMoneda.pack(side=TOP, fill=X)

Util.agregarEtiqueta(frmMoneda, "Moneda", 0, 0)
monedas = []
Util.agregarLista(frmMoneda, monedas, 0, 1)

nb = Notebook(ventana)
nb.pack(fill=BOTH, expand=YES)
pestanas = ["Grafica", "Estadisticas"]
for p in pestanas:
    frm = Frame(ventana)
    nb.add(frm, text=p)

ventana.mainloop()