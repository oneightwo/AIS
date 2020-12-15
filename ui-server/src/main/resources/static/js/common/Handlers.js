changeCriteriaHandler = (input) => {
    let value = parseInt(input.value)
    if (isNaN(value)) value = 0
    if (value < 0) value = 0
    if (value > 5) value = 5
    input.value = value
}