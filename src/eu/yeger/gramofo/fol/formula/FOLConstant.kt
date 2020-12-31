package eu.yeger.gramofo.fol.formula

import eu.yeger.gramofo.model.domain.Node

class FOLConstant(name: String) : FOLFormula(
    type = FOLType.Constant,
    name = name
) {
    override fun getFormulaString(variableAssignments: Map<String, Node>): String {
        return name
    }
}
