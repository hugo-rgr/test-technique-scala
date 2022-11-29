package io.ubilab.result.service

import io.ubilab.result.model.Result

class ResultService {

  private var resultsList: List[Result] = List()

  def addResult(result:Result) = ???


  def seenResult(idResult:Int) = ???

  def unseenResult(idResult:Int) = ???

  def getAllResult():List[Result] = resultsList

  def getAllResultSeen():List[Result] = ???
  def getAllResultUnSeen():List[Result] = ???

  def numberOfEventSeen:Int =  ???
}

object ResultService {

  def build:ResultService = new ResultService
}