export const ShrewdIcon = ({ height = 256, width = 256 }) => {
    return (
        <svg
            width={width}
            height={height}
            viewBox="0 0 200 200"
            xmlns="http://www.w3.org/2000/svg"
            role="img"
            aria-label="Shrewd logo"
        >
            <defs>
                {/* Modern multi-stop gradient */}
                <linearGradient id="shrewdGradient" x1="10%" y1="10%" x2="90%" y2="90%">
                    <stop offset="0%" stopColor="#07765E" />
                    <stop offset="45%" stopColor="#0A8A6F" />
                    <stop offset="100%" stopColor="#14D9B3" />
                </linearGradient>

                {/* Subtle inner shadow for depth */}
                <filter id="innerShadow" x="-20%" y="-20%" width="140%" height="140%">
                    <feGaussianBlur in="SourceAlpha" stdDeviation="3" result="blur" />
                    <feOffset in="blur" dx="1" dy="1" result="offsetBlur" />
                    <feComposite in="SourceGraphic" in2="offsetBlur" operator="over" />
                </filter>

                {/* Subtle highlight effect */}
                <linearGradient id="highlight" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FFFFFF" stopOpacity="0.2" />
                    <stop offset="100%" stopColor="#FFFFFF" stopOpacity="0" />
                </linearGradient>
            </defs>

            {/* Main shape with refined geometry */}
            <path
                d="M100,40 C64.7,40 36,68.7 36,104 C36,139.3 64.7,168 100,168 C135.3,168 164,139.3 164,104 C164,68.7 135.3,40 100,40 Z
                M76,95 Q76,80 91,70 Q106,60 121,70 Q136,80 136,95 Q136,110 121,120 Q106,130 91,120 Q76,110 76,95 Z"
                fill="url(#shrewdGradient)"
                filter="url(#innerShadow)"
            />

            {/* Refined ear element */}
            <path
                d="M136,56 Q150,48 158,62 Q152,72 142,76 Q136,66 136,56 Z"
                fill="#07765E"
                opacity="0.95"
            />

            {/* Refined eye with dynamic highlight */}
            <circle cx="116" cy="90" r="7" fill="#062A20" />
            <circle cx="113" cy="87" r="2" fill="#FFFFFF" opacity="0.9" />

            {/* Subtle highlight overlay */}
            <path
                d="M100,46 C68.7,46 43,71.7 43,103 C43,110.4 44.3,117.5 46.6,124 C76,118 110,124 135,146 C153.7,132.7 164,118.8 164,103 C164,71.7 138.3,46 100,46 Z"
                fill="url(#highlight)"
                opacity="0.15"
            />
        </svg>
    )
}

export const ShrewdHorizontalLookUpIcon = ({ width = 640, height = 192 }) => {
    return (
        <svg
            width={width}
            height={height}
            viewBox="0 0 640 192"
            xmlns="http://www.w3.org/2000/svg"
            role="img"
            aria-label="Shrewd logo lockup"
        >
            <defs>
                <linearGradient id="lockupGradient" x1="10%" y1="10%" x2="90%" y2="90%">
                    <stop offset="0%" stopColor="#07765E" />
                    <stop offset="45%" stopColor="#0A8A6F" />
                    <stop offset="100%" stopColor="#14D9B3" />
                </linearGradient>

                <filter id="textShadow" x="-10%" y="-10%" width="120%" height="120%">
                    <feDropShadow dx="0" dy="1" stdDeviation="0.5" floodOpacity="0.15" />
                </filter>

                <linearGradient id="iconHighlight" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FFFFFF" stopOpacity="0.2" />
                    <stop offset="100%" stopColor="#FFFFFF" stopOpacity="0" />
                </linearGradient>

                <style>
                    {`
                    .shrewd-text {
                        font-family: 'Inter', 'SF Pro Display', -apple-system, system-ui, 'Segoe UI', Roboto, Arial, sans-serif;
                        font-weight: 700;
                        letter-spacing: -0.025em;
                        fill: #062A20;
                        filter: url(#textShadow);
                    }
                    .tagline {
                        font-family: 'Inter', sans-serif;
                        font-weight: 500;
                        letter-spacing: 0.04em;
                        fill: #426962;
                        text-transform: uppercase;
                    }
                    `}
                </style>
            </defs>

            {/* Icon - perfectly positioned */}
            <g transform="translate(32, 20) scale(0.75)">
                <path
                    d="M100,40 C64.7,40 36,68.7 36,104 C36,139.3 64.7,168 100,168 C135.3,168 164,139.3 164,104 C164,68.7 135.3,40 100,40 Z
                    M76,95 Q76,80 91,70 Q106,60 121,70 Q136,80 136,95 Q136,110 121,120 Q106,130 91,120 Q76,110 76,95 Z"
                    fill="url(#lockupGradient)"
                />
                <path
                    d="M136,56 Q150,48 158,62 Q152,72 142,76 Q136,66 136,56 Z"
                    fill="#07765E"
                    opacity="0.95"
                />
                <circle cx="116" cy="90" r="7" fill="#062A20" />
                <circle cx="113" cy="87" r="2" fill="#FFFFFF" opacity="0.9" />
                <path
                    d="M100,46 C68.7,46 43,71.7 43,103 C43,110.4 44.3,117.5 46.6,124 C76,118 110,124 135,146 C153.7,132.7 164,118.8 164,103 C164,71.7 138.3,46 100,46 Z"
                    fill="url(#iconHighlight)"
                    opacity="0.15"
                />
            </g>

            {/* Modern wordmark with refined spacing */}
            <text x="170" y="110" className="shrewd-text" fontSize="78">SHREWD</text>

            {/* Professional tagline with refined positioning */}
            <text x="172" y="142" className="tagline" fontSize="16">
                MODERN WORKFORCE SOLUTIONS
            </text>
        </svg>
    )
}