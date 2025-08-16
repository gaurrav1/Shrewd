export const ShrewdIcon = ({ height = 256, width = 256 }) => {
    return (
        <svg
            width={width}
            height={height}
            viewBox="0 0 256 256"
            xmlns="http://www.w3.org/2000/svg"
            role="img"
            aria-label="Shrewd logo"
        >
            <defs>
                <linearGradient id="shrewdGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FF3B30" />
                    <stop offset="100%" stopColor="#A00" />
                </linearGradient>
                <linearGradient id="circuitPattern" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FFA39C" stopOpacity="0.55" />
                    <stop offset="100%" stopColor="#FF7066" stopOpacity="0.8" />
                </linearGradient>
                <pattern id="techPattern" patternUnits="userSpaceOnUse" width="8" height="8">
                    <path d="M0 0L8 8ZM8 0L0 8Z" stroke="#fff" strokeWidth="0.5" strokeOpacity="0.3" />
                </pattern>
            </defs>

            {/* Shrew head with gradient and tech pattern */}
            <path
                d="M30,168C30,102,86,54,140,54C167,54,185,63,200,78C212,90,218,102,221,114C224,124,224,131,224,136C224,140,226,144,230,147C235,151,241,154,246,156C248,157,248,160,246,161C241,163,234,164,227,163C214,160,203,158,190,159C172,160,156,168,142,175C122,186,106,192,84,193C55,195,30,185,30,168Z"
                fill="url(#shrewdGradient)"
            />

            {/* Tech-enhanced ear */}
            <circle cx="170" cy="86" r="18" fill="url(#shrewdGradient)" />
            <circle
                cx="170"
                cy="86"
                r="9"
                fill="url(#circuitPattern)"
                style={{ mixBlendMode: "lighten" }}
            />

            {/* Futuristic eye */}
            <circle
                cx="182"
                cy="112"
                r="4.5"
                fill="#111"
                opacity="0.9"
            >
                <animate attributeName="r" values="4.5;5;4.5" dur="2s" repeatCount="indefinite" />
            </circle>

            {/* Jaw notch with tech pattern */}
            <path
                d="M146 172C156 167 162 165 168 165C160 171 152 174 144 175Z"
                fill="url(#techPattern)"
                opacity="0.7"
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
                <linearGradient id="lockupGradient" x1="0%" y1="0%" x2="100%" y2="0%">
                    <stop offset="0%" stopColor="#0F0F0F" />
                    <stop offset="100%" stopColor="#333" />
                </linearGradient>
                <linearGradient id="iconGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FF3B30" />
                    <stop offset="100%" stopColor="#C00" />
                </linearGradient>
                <filter id="techGlow" x="-20%" y="-20%" width="140%" height="140%">
                    <feGaussianBlur in="SourceAlpha" stdDeviation="2" result="blur" />
                    <feFlood floodColor="#FF3B30" floodOpacity="0.5" result="glowColor" />
                    <feComposite in="glowColor" in2="blur" operator="in" result="glow" />
                    <feMerge>
                        <feMergeNode in="glow" />
                        <feMergeNode in="SourceGraphic" />
                    </feMerge>
                </filter>
            </defs>

            {/* Tech-enhanced icon */}
            <g transform="translate(8,16)" filter="url(#techGlow)">
                <path
                    d="M0,120C0,58,49,16,102,16C126,16,142,24,156,38C167,49,173,60,176,71C178,80,178,87,178,92C178,96,180,100,184,103C188,106,195,110,200,111C202,112,202,115,200,116C195,118,188,119,181,118C169,115,158,114,145,115C127,116,112,124,99,131C80,141,65,148,45,149C18,151,0,140,0,120Z"
                    fill="url(#iconGradient)"
                />
                <circle cx="124" cy="32" r="14" fill="url(#iconGradient)" />
                <circle
                    cx="124"
                    cy="32"
                    r="7"
                    fill="#FFA39C"
                    opacity="0.55"
                    style={{ mixBlendMode: "lighten" }}
                />
                <circle cx="135" cy="54" r="4" fill="#111" opacity="0.9" />
            </g>

            {/* Modern wordmark */}
            <text
                x="240"
                y="118"
                fontSize="96"
                fill={'var(--svg-color)'}
                fontWeight="800"
                letterSpacing="0.02em"
                fontFamily='Inter, "SF Pro Display", -apple-system, system-ui, "Segoe UI", Roboto, Arial, sans-serif'
            >
                SHREWD
            </text>

            {/* Tech tagline */}
            <text
                x="242"
                y="160"
                fontSize="20"
                fill="#666"
                fontWeight="600"
                fontFamily="Inter, sans-serif"
            >
                SMART ATTENDANCE & PAYROLL
            </text>
        </svg>
    )
}