package io.ubilab.result.service

import io.ubilab.result.model.Result
import io.ubilab.result.model.EventResult

import java.util.Date

class ResultService {

  private var resultsList: List[Result] = List()


  def addResult(result:Result) =
    resultsList.exists(_.id == result.id) match {
      case true => throw new Exception("Erreur: Il y a deja un resultat ayant cet id")
      case false => {
        result.eventResults = new EventResult("created", result.idOwner, new Date()) :: result.eventResults
        resultsList = resultsList :+ result
      }
    }


  def seenResult(idResult:Int) =
    resultsList.find(_.id==idResult) match {
      case Some(res) => {
        res.eventResults = new EventResult("seen", res.idOwner, new Date()) :: res.eventResults
        resultsList = resultsList.updated(resultsList.indexOf(res), res.copy(isSeen=true))
      }
      case None =>
    }


  def unseenResult(idResult:Int) =
    resultsList.find(_.id==idResult) match {
      case Some(res) => {
        res.eventResults = new EventResult("unseen", res.idOwner, new Date()) :: res.eventResults
        resultsList = resultsList.updated(resultsList.indexOf(res), res.copy(isSeen=false))
      }
      case None =>
    }

  def getResultById(idResult: Int):Result =
    resultsList.find(_.id==idResult) match {
      case Some(res) => return res
      case None => throw new Exception("Erreur: Pas de résultat pour cet id")
    }

  def getAllResult():List[Result] = resultsList

  def getAllResultSeen():List[Result] = 
    resultsList.filter(_.isSeen)

  def getAllResultUnSeen():List[Result] =
    resultsList.filterNot(_.isSeen)

  def getAllResultSorted():List[Result] =
    resultsList.sortBy(_.eventResults.head.createdAt.getTime)

  def getEventDateByIds(idEvent: String, idResult: Int): Date =
    getResultById(idResult).eventResults.find(_.id==idEvent) match {
      case Some(event) => return event.createdAt
      case None => throw new Exception("Erreur: Pas de date pour ces ids")
    } 

  def numberOfEventSeen:Int = {
    var somme: Int = 0
    for(result <- resultsList){
      somme+= result.eventResults.filter(_.id=="seen").count(_ => true)
    }
    return somme
  }

}


object ResultService {

  def build:ResultService = new ResultService
}