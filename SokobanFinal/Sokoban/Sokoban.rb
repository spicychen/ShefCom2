require 'io/console'
require 'console_splash'
require 'colorize'
# the splash screen
def cover
  puts "\e[1;1H\e[2J"
  splash = ConsoleSplash.new(25,30)
  splash.write_header("Sokoban", "Rick (Junjin)", "5.0",{:nameFg=>:red, :authorFg=>:green, :versionFg=>:yellow})
  splash.write_line(-13, "@copyright 2015", {:start => 7})
  splash.write_line(-10, "press:", {:start => 5})
  splash.write_line(-8, "s to start", {:start => 5})
  splash.write_line(-6, "l to select level", {:start => 5})
  splash.write_line(-4, "q to quit", {:start => 5})
  splash.write_line(-2, "h to get help", {:start => 5})
  splash.write_horizontal_pattern(" ", {:bg=>:green})
  splash.write_vertical_pattern(" ", {:bg=>:green})
  splash.splash
end
#helpping interface
def help
  presentation
  print ' '.colorize(:background => :green)
  puts "   Player(@)"
  print ' '.colorize(:background => :green)
  puts "   should push the box($)"
  print ' '.colorize(:background => :green)
  puts "   in storage place(.)"
  puts ''
  print ' '.colorize(:background => :green)
  puts "    to go up: w"
  puts ''
  print ' '.colorize(:background => :green)
  puts "    to go down: s"
  puts ''
  print ' '.colorize(:background => :green)
  puts "    to go left: a"
  puts ''
  print ' '.colorize(:background => :green)
  puts "    to go right: d"
  puts ''
  print ' '.colorize(:background => :green)
  puts "    any key to go back"
  puts ''
  goback = STDIN.getch
  home()
end
#win interface
def win
  presentation
  print ' '.colorize(:background => :green)
  puts "    You WIN!!!"
  print ' '.colorize(:background => :green)
  puts "    any key to continue"
  continue = STDIN.getch
end
#set colours here!
def colour
  player = '@'.colorize(:cyan)
  virtualPlayer = '+'.colorize(:background => :red)
  box  = '$'.colorize(:color => :magenta, :background => :blue)
  wall = '#'.colorize(:yellow)
  storage  = '.'.colorize(:color => :blue, :background => :red)
  goodBox = '*'.colorize(:color => :red, :background => :green)
  return player, virtualPlayer, box, wall, storage, goodBox
end
#update the map
def render(gameMap)
  (0..gameMap.length-1).each do |i|
    print ' '.colorize(:background => :green)
    print '   '
    gameMap[i].each do|f|
      print f
    end
  end
end
#something about decoration
def presentation
  puts "\e[1;1H\e[2J"
  puts ''
  puts ''
  puts ''
end
def gamepresentation(gameMap,n,ub)
  puts ''

  puts ''
  render(gameMap)
  print ' '.colorize(:background => :green)
  puts "     #{ub} box(es) left"
  print ' '.colorize(:background => :green)
  puts "         level #{n}"
  print ' '.colorize(:background => :green)
  puts "  r to reset, q to quit"
end
#set level locate player
def level(n)
  puts "\e[1;1H\e[2J"
  ch, vp, bo, wa, st, gb = colour
  map = File.readlines "levels/levels/level#{n}.xsb"
  x=0
  y=0
  ub=0
  gameMap = Array[]
  (0..map.length-1).each do |i|
    gameMap[i] = map[i].split(//)
    (0..gameMap[i].length-1).each do |j|
      if gameMap[i][j] == '@'
        gameMap[i][j] = ch
        x = i
        y = j
      elsif gameMap[i][j] == '$'
        gameMap[i][j] = bo
        ub +=1
      elsif gameMap[i][j] == '#'
        gameMap[i][j] = wa
      elsif gameMap[i][j] == '.'
        gameMap[i][j] = st
      elsif gameMap[i][j] == '*'
        gameMap[i][j] = gb
      elsif gameMap[i][j] == '+'
        gameMap[i][j] = vp
        x = i
        y = j
      end

    end
  end
  gamepresentation(gameMap,n,ub)
  gameloop(x, y, gameMap, ub, n)
end
#menu
def home()
  cover
  command = STDIN.getch
  if command == 's'
    presentation
    level(1)
    n = 1
  elsif command == 'l'
    presentation
    print ' '.colorize(:background => :green)
    puts '     which level?(1-50)'.colorize(:yellow)
    print ' '.colorize(:background => :green)
    print '            '
    n = gets.chomp.to_i
    if n <= 50
    level(n)
    else
      home()
    end
  elsif command == 'h'
    help
    # else
    #   home()
  end
end
#Play the game!
def gameloop(x, y, gameMap, ub, n)
  vir = false
  #get colour
  ch, vp, bo, wa, st, gb = colour
  begin
    g = STDIN.getch
    puts "\e[1;1H\e[2J"
    #check whether it is virtual environment (player in storagePlace) or not
    if vir
      gameMap[x][y] = st
    else
      gameMap[x][y] = ' '
    end

    vir = false
    #up
    if g == 'w' && gameMap[x-1][y] != wa

      x -= 1
      if gameMap[x][y] == bo
        if gameMap[x-1][y] == ' '
          gameMap[x-1][y] = bo
        elsif gameMap[x-1][y] == wa || gameMap[x-1][y] == bo || gameMap[x-1][y] == gb
          x +=1
        elsif gameMap[x-1][y] == st
          gameMap[x-1][y] = gb
          ub -=1
        end
      elsif gameMap[x][y] == gb
        if gameMap[x-1][y] == ' '
          gameMap[x-1][y] = bo
          ub +=1
        elsif gameMap[x-1][y] == wa || gameMap[x-1][y] == bo || gameMap[x-1][y] == gb
          x +=1
        elsif gameMap[x-1][y] == st
          vir = true
          gameMap[x-1][y] = gb
        end
      end

    end
    #down
    if g == 's' && gameMap[x+1][y] != wa
      x += 1
      if gameMap[x][y] == bo
        if gameMap[x+1][y] == ' '
          gameMap[x+1][y] = bo
        elsif gameMap[x+1][y] == wa || gameMap[x+1][y] == bo || gameMap[x+1][y] == gb
          x -=1
        elsif gameMap[x+1][y] == st
          gameMap[x+1][y] = gb
          ub -=1
        end
      elsif gameMap[x][y] == gb
        if gameMap[x+1][y] == ' '
          gameMap[x+1][y] = bo
          ub +=1
        elsif gameMap[x+1][y] == wa || gameMap[x+1][y] == bo || gameMap[x+1][y] == gb
          x -=1
        elsif gameMap[x+1][y] == st
          vir = true
          gameMap[x+1][y] = gb
        end
      end
    end
    #left
    if g == 'a' && gameMap[x][y-1] != wa
      y -= 1
      if gameMap[x][y] == bo
        if gameMap[x][y-1] == ' '
          gameMap[x][y-1] = bo
        elsif gameMap[x][y-1] == wa || gameMap[x][y-1] == bo || gameMap[x][y-1] == gb
          y +=1
        elsif gameMap[x][y-1] == st
          gameMap[x][y-1] = gb
          ub -=1
        end
      elsif gameMap[x][y] == gb
        if gameMap[x][y-1] == ' '
          gameMap[x][y-1] = bo
          ub +=1
        elsif gameMap[x][y-1] == wa || gameMap[x][y-1] == bo || gameMap[x][y-1] == gb
          y +=1
        elsif gameMap[x][y-1] == st
          vir = true
          gameMap[x][y-1] = gb
        end
      end
    end
    #right
    if g == 'd' && gameMap[x][y+1] != wa

      y += 1
      if gameMap[x][y] == bo
        if gameMap[x][y+1] == ' '
          gameMap[x][y+1] = bo
        elsif gameMap[x][y+1] == wa || gameMap[x][y+1] == bo || gameMap[x][y+1] == gb
          y -=1
        elsif gameMap[x][y+1] == st
          gameMap[x][y+1] = gb
          ub -=1
        end
      elsif gameMap[x][y] == gb
        if gameMap[x][y+1] == ' '
          gameMap[x][y+1] = bo
          ub +=1
        elsif gameMap[x][y+1] == wa || gameMap[x][y+1] == bo || gameMap[x][y+1] == gb
          y -=1
        elsif gameMap[x][y+1] == st
          vir = true
          gameMap[x][y+1] = gb
        end
      end

    end

    if gameMap[x][y] == ' '
      vir = false
    elsif gameMap[x][y] == st || gameMap[x][y] == gb
      vir = true
    end
    if vir
      gameMap[x][y] = vp
    elsif
    gameMap[x][y] = ch
    end
    gamepresentation(gameMap,n,ub)
  end while g != 'q' && ub !=0 && g!='r'
  #possible ending
  if ub == 0
    win
    if n != 50
      level(n+1)
    else
      home()
    end
  elsif g=='r'
    level(n)
  else
    home()
  end
end

home()
