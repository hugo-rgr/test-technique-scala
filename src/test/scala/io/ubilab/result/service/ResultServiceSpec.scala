package io.ubilab.result.service

import io.ubilab.result.model.Result
import org.scalatest.matchers.must.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

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
    pending
    // init le service avec 3 resultats

    it("devrait avoir une liste de 3 resultats non vue aprés l'ajout de 3 resultat.") {
      true shouldEqual false
    }

    it("ne devrait pas authorisé l'ajout d'un résultats avec un id existant") {
      true shouldEqual false
    }

    it("devrait avoir 1 resultats vue dans la liste aprés la vision d'un resultat") {
      true shouldEqual false
    }

    it("devrait avoir les 3 resultats vue dans la liste aprés qu'il soit tous vue") {
      true shouldEqual false
    }

    it("devrait avoir plus que 2 resultats vue dans la liste aprés qu'il soit tous vue puis 1 ou la vue est enlevé") {
      true shouldEqual false
    }

    it("ne devrait pas planté aprés la vision d\\'un resultat non ajouté") {
      true shouldEqual false
    }

  }


  describe("Après l'ajout de 3 résultats,") {
    pending
    // init le service avec 3 resultats
    it("devrait avoir la list des résultat dans l'order de création ( en se basant sur les events de création)") {
      true shouldEqual false
    }

    it("devrait avoir 1 event a la date de maintenant quand 1 résultat est vue") {
      true shouldEqual false
    }

    it("devrait avoir 2 events avec 2 dates différent aprés la vision d'un resultat puis la suppression de la vision") {
      true shouldEqual false
    }

    it("devrait avoir une fonction qui retourne une liste ordonnée des resultats par rapport au dernier modifier") {
      true shouldEqual false
    }
  }


  describe("N'hésitez pas a proposer de nouveaux tests") {}
}
