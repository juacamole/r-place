import React, { useState } from 'react';

// Define the function within the same file
const parseFraction = (input: string): number[] => {
    // Remove the parentheses and split the string by the slash
    const parts = input.replace(/[()]/g, '').split('/');

    // Convert the string parts to numbers and return
    return parts.map(part => parseInt(part, 10));
};

const FractionParser: React.FC = () => {
    const [fractionString, setFractionString] = useState<string>("(1/2)");
    const [numberArray, setNumberArray] = useState<number[]>([]);

    const handleParse = () => {
        const result = parseFraction(fractionString);
        setNumberArray(result);
    };

    return (
        <div>
            <input
                type="text"
                value={fractionString}
                onChange={(e) => setFractionString(e.target.value)}
            />
            <button onClick={handleParse}>Parse Fraction</button>
            <div>
                Parsed Numbers: {numberArray.join(', ')}
            </div>
        </div>
    );
};

export default FractionParser;
