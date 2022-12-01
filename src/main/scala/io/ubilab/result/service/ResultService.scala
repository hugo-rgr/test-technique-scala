package io.ubilab.result.service

import io.ubilab.result.model.Result

class ResultService {

  private var resultsList: List[Result] = List()

  def addResult(result:Result) =
    resultsList.exists(_.id == result.id) match {
      case true => throw new Exception("Il y a deja un resultat ayant cet id")
      case false => resultsList = result :: resultsList
    }


  def seenUpdate(idResult:Int, seen:Boolean) =
    resultsList.find(_.id==idResult) match {
      case Some(res) => resultsList = resultsList.updated(resultsList.indexOf(res), res.copy(isSeen=seen))
      case None =>
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