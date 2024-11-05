package ar.edu.unicen.seminario.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
//hilt permite crear distintos modulos de dependencias
//en esos modulos definir como inyectar ciertos componentes de la app
class SeminarioApp: Application() {
//el oncreate es lo primero q se ejecute antes incluso de crear los activities


}