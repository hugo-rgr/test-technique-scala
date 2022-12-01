package io.ubilab.result.service

import io.ubilab.result.model.Result
import io.ubilab.result.model.EventResult
import org.scalatest.matchers.must.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.util.Date

class ResultServiceSpec extends AnyFunSpec with Matchers {

  describe("Step 1 : initialisation du projet avec 0 et 1 resultat") {

    val resultService = ResultService.build

    it("devrait être initialisé avec une liste de résultat vide") {

      resultService.getAllResult shouldEqual List()
    }
  }

  describe("Après l'ajout d'un résultat,") {

    val resultService = ResultService.build

    resultService.addResult(
      Result(
        46,
        76,
        List(42),
        false,
        Nil,
        "test"
      )
    )

    it("devrait avoir une liste de 1 résultat non vue") {

      resultService.getAllResult.length shouldEqual 1

    }

    it("devrait avoir une liste de 1 résultat vue aprés la vision de ce résultat") {

      resultService.seenResult(46)
      resultService.getAllResultSeen().length shouldEqual 1
      resultService.getAllResult().head.isSeen shouldEqual true
    }

  }

  describe("Après l'ajout de 3 résultats,") {
    // init le service avec 3 resultats
    val resultService = ResultService.build

    resultService.addResult(Result(46,76,List(42),false,Nil,"test1"))
    resultService.addResult(Result(3,98,List(46),false,Nil,"test2"))
    resultService.addResult(Result(59,70,List(38),false,Nil,"test3"))

    it("devrait avoir une liste de 3 resultats non vue aprés l'ajout de 3 resultat.") {
      resultService.getAllResultUnSeen().length shouldEqual 3
    }

    it("ne devrait pas authorisé l'ajout d'un résultats avec un id existant") {
      val resultNotAuthorized = Result(59,75,List(50),false,Nil,"test4")
      an [Exception] should be thrownBy resultService.addResult(resultNotAuthorized)
    }

    it("devrait avoir 1 resultats vue dans la liste aprés la vision d'un resultat") {
      resultService.seenResult(46)
      resultService.getAllResultSeen().length shouldEqual 1
    }

    it("devrait avoir les 3 resultats vue dans la liste aprés qu'il soit tous vue") {
      resultService.seenResult(3)
      resultService.seenResult(59)
      resultService.getAllResultSeen().length shouldEqual 3
    }

    it("devrait avoir plus que 2 resultats vue dans la liste aprés qu'il soit tous vue puis 1 ou la vue est enlevé") {
      resultService.getAllResultSeen().length shouldEqual 3
      resultService.unseenResult(46)
      resultService.getAllResultSeen.length shouldEqual 2
    }

    it("ne devrait pas planté aprés la vision d\\'un resultat non ajouté") {
      val resNotAdded = Result(30,50,List(38),false,Nil,"test5")
      noException should be thrownBy resultService.seenResult(resNotAdded.id)
    }

  }


  describe("Après l'ajout de 3 résultats,") {
    // init le service avec 3 resultats
    val resultService = ResultService.build
    val result1 = Result(46,76,List(42),false,Nil,"test1")
    Thread.sleep(10)
    val result2 = Result(3,98,List(46),false,Nil,"test2")
    Thread.sleep(10)
    val result3 = Result(59,70,List(38),false,Nil,"test3")
    resultService.addResult(result1)
    resultService.addResult(result2)
    resultService.addResult(result3)

    it("devrait avoir la list des résultat dans l'order de création ( en se basant sur les events de création)") {
      resultService.getAllResult shouldEqual List(result1, result2, result3)
    }

    it("devrait avoir 1 event a la date de maintenant quand 1 résultat est vue") {
      Thread.sleep(10)
      resultService.seenResult(result1.id)
      resultService.getAllResultSeen.length shouldEqual 1

      val seenEvent: EventResult = resultService.getAllResultSeen.head.eventResults.last
      seenEvent.id shouldBe a ["seen"]

      val currentDate: Date = new Date()
      seenEvent.createdAt.getTime shouldEqual currentDate.getTime +- 2000
    }

    it("devrait avoir 2 events avec 2 dates différent aprés la vision d'un resultat puis la suppression de la vision") {
      val seenEvent = resultService.getAllResult.head.eventResults.last
      seenEvent.id shouldBe a ["seen"]

      Thread.sleep(1000)
      resultService.unseenResult(result1.id)

      val unseenEvent = resultService.getAllResult.last.eventResults.last
      unseenEvent.id shouldBe a ["unseen"]

      seenEvent.createdAt.getTime should not equal unseenEvent.createdAt.getTime
    }

    it("devrait avoir une fonction qui retourne une liste ordonnée des resultats par rapport au dernier modifier") {
      resultService.getAllResultSorted shouldEqual List(result2, result3, result1)
    }

    it("devrait avoir une fonction qui retourne le nombre d'événements vus") {
      resultService.numberOfEventSeen shouldEqual 1

      resultService.seenResult(result1.id)
      resultService.numberOfEventSeen shouldEqual 2

      resultService.seenResult(result2.id)
      resultService.numberOfEventSeen shouldEqual 3

      resultService.seenResult(result2.id)
      resultService.numberOfEventSeen shouldEqual 4

      resultService.seenResult(result3.id)
      resultService.numberOfEventSeen shouldEqual 5
    }
  }


  describe("N'hésitez pas a proposer de nouveaux tests") {}
}
