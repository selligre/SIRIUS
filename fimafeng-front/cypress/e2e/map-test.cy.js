describe('map test', () => {
  it('passes', () => {
    const baseUrl = Cypress.env('baseUrl');
    cy.visit(`${baseUrl}/map`);

    cy.get('.search-input').type('au')
    cy.get('.search-input').should('have.value', 'au')
    cy.wait(1000)
    cy.get('.search-button').click()
    cy.get('.overlay-content').should('be.visible')
    cy.wait(1000)
    cy.get('.clear-button').click()

    cy.get('.toggle-button').click()
    cy.get('.tag-selector').should('be.visible')
    cy.get('#tag-6').should('not.be.checked')
    cy.get('#tag-5').should('not.be.checked')
    cy.wait(1000)

    cy.get('.tag-selector [type="checkbox"]').not('[disabled]').check(['5', '6'])
    cy.get('#tag-6').should('be.checked')
    cy.get('#tag-5').should('be.checked')
    cy.wait(1000)

    cy.get('.toggle-button').click()
    cy.wait(1000)
    cy.get('.toggle-button').click()
    cy.get('#tag-6').should('be.checked')
    cy.get('#tag-5').should('be.checked')
    cy.wait(1000)

    cy.get('.search-button').click()
    cy.get('.overlay-content').should('be.visible')
    cy.wait(1000)

    cy.get('.overlay-content').scrollTo('bottom')
    cy.wait(1000)

    cy.get('.clear-button').click()
    cy.wait(1000)

    cy.get('.toggle-button').click()
    cy.wait(1000)

    cy.get('.leaflet-container').click()

    cy.get('.overlay-district-content').should('be.visible')
  })
})