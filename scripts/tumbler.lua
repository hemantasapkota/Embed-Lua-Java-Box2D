function init(world, cam)
end

function keyPressed(world, cam, body, joint, key)
    if key == "Right" then
        cam:translate(0.5, 0, 0)
    end

    if key == "Left" then
        cam:translate(-0.5, 0, 0)
    end

    if key == "A" then
        joint:setMotorSpeed(1)
    end
end
