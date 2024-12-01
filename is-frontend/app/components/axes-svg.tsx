import React from 'react';

export default function CoordinateAxes({width, height, margin, tickWidth = 5, scaleStep = 20, scale = 1}: {
    width: number,
    height: number,
    margin: number,
    tickWidth?: number,
    scaleStep?: number,
    scale?: number
}) {

    const renderTicks = (axisLength: number, step: number, isVertical: boolean) => {
        const ticks = [];
        for (let i = -axisLength; i <= axisLength; i += step) {
            const position = i + axisLength; // Shift to positive range
            if (isVertical) {
                ticks.push(
                    <line
                        key={position}
                        x1={width / 2 + tickWidth / 2}
                        y1={position}
                        x2={width / 2 - tickWidth / 2}
                        y2={position}
                        stroke="black"
                    />
                );
                ticks.push(
                    <text key={`label-${position}`} x={width / 2 + 5} y={position + 5} fontSize="10">
                        {(i * scale) | 0}
                    </text>
                );
            } else {
                ticks.push(
                    <line
                        key={position}
                        x1={position}
                        y1={height / 2 + tickWidth / 2}
                        x2={position}
                        y2={height / 2 - tickWidth / 2}
                        stroke="black"
                    />
                );
                ticks.push(
                    <text key={`label-${position}`} x={position - 5} y={height / 2 + 15} fontSize="10">
                        {i * scale | 0}
                    </text>
                );
            }
        }
        return ticks;
    };

    return (
        <svg width={width} height={height}>
            <line x1={margin} y1={height / 2} x2={width - margin} y2={height / 2} stroke="black" />
            <line x1={width / 2} y1={margin} x2={width / 2} y2={height - margin} stroke="black" />

            {renderTicks(width / 2, scaleStep, false)}
            {renderTicks(height / 2, scaleStep, true)}
        </svg>
    );
};