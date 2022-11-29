package io.ubilab.result.service

import io.ubilab.result.model.Result

class ResultService {

  private var resultsList: List[Result] = List()

  def addResult(result:Result) =
    resultsList = result :: resultsList


  def seenResult(idResult:Int) =
    for(res <- resultsList){
      if(res.id == idResult){
        val resIndex: Int = resultsList.indexOf(res)
        val resCopy = res.copy(isSeen=true) //preserve full immutability of Result objects (no turning isSeen into a var)
        resultsList = resultsList.updated(resIndex,resCopy)
      }
    }

  def unseenResult(idResult:Int) = ???

  def getAllResult():List[Result] = resultsList

  def getAllResultSeen():List[Result] = 
    resultsList.filter(_.isSeen)

  def getAllResultUnSeen():List[Result] = ???

  def numberOfEventSeen:Int =  ???
}

object ResultService {

  def build:ResultService = new ResultService
}