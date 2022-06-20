package br.remoteck.movietoyou.data.repository

enum class Status {
  RUNNING, SUCCESS, FAILED
}

class NetworkState(val status: Status, val message: String) {

  companion object {
    val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Loaded successfully!")
    val LOADING: NetworkState = NetworkState(Status.RUNNING, "Loading...")
    val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong!")
    val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "No more pages here!")
  }

}