package io.ubilab.result.service

import io.ubilab.result.model.Result

class ResultService {

  private var resultsList: List[Result] = List()


  def addResult(result:Result) = {
    var idExists: Boolean = false
    var i: Int = 0
    val resLength: Int = getAllResult.length

    while(idExists == false && i != resLength){
      if(resultsList(i).id == result.id){
        System.err.println("ERREUR : l'id est deja existant")
        idExists = true
      }
      i+= 1
    }

    if(idExists == false){
      resultsList = result :: resultsList
    }
  }


  def seenUpdate(idResult:Int, seen:Boolean) = {
    var idSeen: Boolean = false
    var i: Int = 0
    val listLength: Int = getAllResult.length

    while(idSeen == false && i != listLength){ //id is unique, no need to continue when it is found
      var res = resultsList(i)

      if(res.id == idResult){
        val resCopy = res.copy(isSeen=seen) //preserve full immutability of Result objects (no turning isSeen into a var)
        resultsList = resultsList.updated(i,resCopy)
        idSeen = true
      }

      i+=1
    }
  }

  def seenResult(idResult:Int) =
    seenUpdate(idResult, seen=true)

  def unseenResult(idResult:Int) =
    seenUpdate(idResult, seen=false)


  def getAllResult():List[Result] = resultsList

  def getAllResultSeen():List[Result] = 
    resultsList.filter(_.isSeen)

  def getAllResultUnSeen():List[Result] =
    resultsList.filterNot(_.isSeen)

  def numberOfEventSeen:Int =  ???
}


object ResultService {

  def build:ResultService = new ResultService
}