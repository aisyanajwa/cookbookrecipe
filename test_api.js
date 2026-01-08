
try {
    const fetch = require('node-fetch'); // You might not have node-fetch, use built-in fetch if Node 18+
} catch (e) { }

async function testMealPlan() {
    const BASE_URL = 'http://localhost:8080/api/mealplans';
    const TEST_USER_ID = '12'; // Assuming user 12 exists from previous context or I'll try 1
    const RECIPE_ID = '3'; // Assuming a recipe exists

    console.log("1. Adding meal with recipe...");
    const addRes = await fetch(`${BASE_URL}/${TEST_USER_ID}/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            day: 'Monday',
            menu: 'Test Meal With Recipe',
            recipeId: RECIPE_ID
        })
    });
    const addData = await addRes.json();
    console.log("Add Response:", JSON.stringify(addData, null, 2));

    if (addData && addData.recipe) {
        console.log("SUCCESS: Add response contains recipe.");
    } else {
        console.log("FAILURE: Add response MISSING recipe.");
    }

    console.log("2. Fetching all meals...");
    const getRes = await fetch(`${BASE_URL}/${TEST_USER_ID}`);
    const getData = await getRes.json();
    console.log("Get Response (first item):", JSON.stringify(getData[getData.length - 1], null, 2));

    const lastMeal = getData.find(m => m.mealplanId === addData.mealplanId);
    if (lastMeal && lastMeal.recipe) {
        console.log("SUCCESS: Get response contains recipe.");
    } else {
        console.log("FAILURE: Get response MISSING recipe.");
    }
}

testMealPlan();
